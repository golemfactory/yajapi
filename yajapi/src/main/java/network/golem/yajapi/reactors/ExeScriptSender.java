package network.golem.yajapi.reactors;

import network.golem.yajapi.FutureImpl;
import network.golem.yajapi.activity.models.ExeScriptCommandResult;
import network.golem.yajapi.activity.models.ExeScriptRequest;
import network.golem.yajapi.adapter.ApiException;
import network.golem.yajapi.adapter.ApiInitializer;
import network.golem.yajapi.adapter.SendAdapter;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;

//TODO wait for subthread to complete
public class ExeScriptSender {
    private static ExeScriptSender instance;
    public synchronized static ExeScriptSender getInstance() {
        if (instance == null) {
            instance = new ExeScriptSender();
        }
        return instance;
    }

    private final network.golem.yajapi.activity.apis.RequestorControlApi activityRequestorControlApi = new network.golem.yajapi.activity.apis.RequestorControlApi();
    private final SendAdapter sendAdapter = SendAdapter.getInstance();
    private final Object mutex = new Object();
    private final List<ActiveBatch> activeBatches = new ArrayList<>();
    private boolean broken = false;

    private ExeScriptSender() {
        ApiInitializer.initialize();
        new Thread(() -> {
            checkForBatchesResults();
        }).start();
    }

    private void checkForBatchesResults() {
        while (true) {
            System.out.println("checkForBatchesResults");
            synchronized (mutex) {
                System.out.println("synced");
                if (broken) {
                    for (ActiveBatch activeBatch : activeBatches) {
                        activeBatch.result.setSuccess(false);
                        if (activeBatch.future != null) {
                            System.out.println("future");
                            activeBatch.future.setValue(activeBatch.result);
                        } else {
                            System.out.println("queue");
                            try {
                                activeBatch.queue.put(activeBatch.result);
                            } catch (InterruptedException e) {
                                try {
                                    activeBatch.queue.put(activeBatch.result);
                                } catch (InterruptedException interruptedException) {
                                    System.err.println("Could not put batch into the queue: "+activeBatch.result.getBatchId()+". The script is abandoned");
                                }
                            }
                        }
                    }
                    return;
                }
                System.out.println("activeBatches.size="+activeBatches.size());
                if (activeBatches.size() == 0) {
                    try {
                        System.out.println("gonna wait");
                        mutex.wait();  //waiting for active batches
                        System.out.println("released");
                    } catch (InterruptedException e) {
                        e.printStackTrace(); //the exception is ignored
                    }
                    continue;
                }
                Collections.sort(activeBatches, (o1, o2) -> Long.compare(o1.nextCheck, o2.nextCheck));
                ActiveBatch activeBatch = activeBatches.get(0);
                long now = System.currentTimeMillis();
                if (now < activeBatch.nextCheck) {
                    try {
                        mutex.wait(activeBatch.nextCheck-now);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //we ignore interruptions
                    }
                    continue;  //some other calls could arrived meanwhile
                }
                List<ExeScriptCommandResult> execBatchResults;
                try {
                    System.out.println("pre");
                    execBatchResults = activityRequestorControlApi.getExecBatchResults(activeBatch.result.getActivityId(), activeBatch.result.getBatchId(), null, null);
                    System.out.println("post");
                    boolean isBatchFinished = false;
                    for (ExeScriptCommandResult execBatchResult : execBatchResults) {
                        System.out.println(execBatchResult);
                        if (execBatchResult.isIsBatchFinished()) isBatchFinished = true;
                        if (execBatchResult.getResult() == ExeScriptCommandResult.ResultEnum.ERROR) {
                            activeBatch.result.getErrorResults().add(execBatchResult);
                        } else {
                            activeBatch.result.getOkResults().add(execBatchResult);
                        }
                        if (execBatchResult.getStdout() != null) System.out.println(execBatchResult.getStdout());
                        if (execBatchResult.getStderr() != null) System.err.println(execBatchResult.getStderr());
                    }
                    System.out.println("time="+new Date());
                    if (!isBatchFinished) {
                        System.out.println("batch not finished");
                        activeBatch.nextCheck = System.currentTimeMillis()+activeBatch.timeout;
                        continue;
                    }
                    System.out.println("size1="+activeBatches.size());
                    activeBatches.remove(0);
                    System.out.println("size2="+activeBatches.size());
                    activeBatch.result.setSuccess(activeBatch.result.getErrorResults().isEmpty());
                } catch (network.golem.yajapi.activity.ApiException e) {
                    activeBatches.remove(0);
                    activeBatch.result.setApiException(e);
                    activeBatch.result.setSuccess(false);
                }
                if (activeBatch.future != null) {
                    System.out.println("future");
                    activeBatch.future.setValue(activeBatch.result);
                } else {
                    System.out.println("queue");
                    try {
                        activeBatch.queue.put(activeBatch.result);
                    } catch (InterruptedException e) {
                        try {
                            activeBatch.queue.put(activeBatch.result);
                        } catch (InterruptedException interruptedException) {
                            System.err.println("Could not put batch into the queue: "+activeBatch.result.getBatchId()+". The script is abandoned");
                        }
                    }
                }
            }
        }
    }

    public Future<ExeScriptResult> sendExeScript(ExeScriptRequest script, String activityId, float timeout) throws ApiException {
        if (timeout <= 0) {
            throw new IllegalArgumentException();
        }
        synchronized (mutex) {
            String batchId = sendAdapter.exec(script, activityId);
            ExeScriptResult exeScriptResult = new ExeScriptResult(activityId, batchId);
            ActiveBatch activeBatch = new ActiveBatch(exeScriptResult, (long) (timeout * 1000));
            FutureImpl<ExeScriptResult> future = new FutureImpl<>();
            activeBatch.future = future;
            activeBatches.add(activeBatch);
            mutex.notify(); // in some cases ....
            return future;
        }
    }

    public String sendExeScript(ExeScriptRequest script, String activityId, float timeout, BlockingQueue<ExeScriptResult> queue) throws ApiException {
        if (timeout <= 0) {
            throw new IllegalArgumentException();
        }
        synchronized (mutex) {
            String batchId = sendAdapter.exec(script, activityId);
            ExeScriptResult exeScriptResult = new ExeScriptResult(activityId, batchId);
            ActiveBatch activeBatch = new ActiveBatch(exeScriptResult, (long) (timeout * 1000));
            activeBatch.queue = queue;
            activeBatches.add(activeBatch);
            mutex.notify(); // in some cases ....
            return batchId;
        }
    }

    public void close() {
        synchronized (mutex) {
            if (broken) return;
            broken = true;
            mutex.notify(); // in some cases ....
        }
    }

    private static class ActiveBatch {
        ExeScriptResult result;
        long timeout;  //in milisec
        long nextCheck;
        FutureImpl<ExeScriptResult> future;
        BlockingQueue<ExeScriptResult> queue;

        public ActiveBatch(ExeScriptResult result, long timeout) {
            this.result = result;
            this.timeout = timeout;
            nextCheck = System.currentTimeMillis()+timeout;
        }
    }
}
