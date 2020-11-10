package network.golem.yajapi.reactors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import network.golem.yajapi.adapter.ApiInitializer;
import network.golem.yajapi.adapter.SendAdapter;
import network.golem.yajapi.market.ApiException;
import network.golem.yajapi.market.models.Proposal;

import java.util.*;
import java.util.concurrent.BlockingQueue;

//TODO wait for subthread to complete
public class ProposalsCollector {
    private static ProposalsCollector instance;

    public synchronized static ProposalsCollector getInstance() {
        if (instance == null) {
            instance = new ProposalsCollector();
        }
        return instance;
    }

    private final network.golem.yajapi.market.apis.RequestorApi marketRequestorApi = new network.golem.yajapi.market.apis.RequestorApi();
    private final Object mutex = new Object();
    private final LinkedList<ActiveSubscription> activeSubscriptions = new LinkedList<>();
    private boolean broken = false;

    private ProposalsCollector() {
        ApiInitializer.initialize();
        new Thread(() -> {
            checkForProposals();
        }).start();
    }

    private void checkForProposals() {
        ObjectMapper objectMapper = new ObjectMapper();
        while (true) {
            System.out.println("checkForProposals");
            synchronized (mutex) {
                System.out.println("synced cp");
                if (broken) {
                    for (Iterator<ActiveSubscription> it = activeSubscriptions.iterator(); it.hasNext(); ) {
                        ActiveSubscription activeSubscription = it.next();
                        try {
                            activeSubscription.queue.put(new CollectedProposal(activeSubscription.demandId, new ProposalsCollectingException("the collector is halted")));
                        } catch (InterruptedException e) {
                            e.printStackTrace();   //we ignore this exception, it should not happen
                        }
                        it.remove();
                    }
                    return;
                }
                if (activeSubscriptions.size() == 0) {
                    try {
                        mutex.wait();
                    } catch (InterruptedException e) {
                        //  interruptions are ignored
                    }
                    continue;
                }
                ActiveSubscription activeSubscription = activeSubscriptions.removeFirst();
                activeSubscriptions.addLast(activeSubscription);    //the element is moved to the end
                try {
                    List<Object> offers = marketRequestorApi.collectOffers(activeSubscription.demandId, 1f, null);
                    System.out.println("collected offers "+offers.size());
                    for (Object offer : offers) {
                        Map rawProposal = (Map) ((Map)offer).get("proposal");
                        String serializedProposal = objectMapper.writeValueAsString(rawProposal);
                        Proposal proposal = new ObjectMapper().readValue(serializedProposal, Proposal.class);
                        try {
                            activeSubscription.queue.put(new CollectedProposal(activeSubscription.demandId, proposal));
                        } catch (InterruptedException e) {
                            e.printStackTrace();  //we ignore interruptions, the method should not block, the proposal is lost if the exception is caught
                        }
                    }
                } catch (Exception e) {
                    try {
                        activeSubscription.queue.put(new CollectedProposal(activeSubscription.demandId, new ProposalsCollectingException(e)));
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();  //we ignore this interruption, it should not happen
                    }
                    activeSubscriptions.removeLast();
                }
            }
        }
    }

    public void startCollecting(String demandId, BlockingQueue<CollectedProposal> queue) {
        synchronized (mutex) {
            for (ActiveSubscription activeSubscription : activeSubscriptions) {
                if (activeSubscription.demandId.equals(demandId)) {
                    throw new IllegalStateException("demand already registered");
                }
            }
            if (activeSubscriptions.size() == 0) {
                mutex.notify();  //collecting thread is supposed to wait
            }
            activeSubscriptions.add(new ActiveSubscription(demandId, queue));
        }
    }

    public void stopCollecting(String demandId) {
        synchronized (mutex) {
            for (Iterator<ActiveSubscription> it = activeSubscriptions.iterator(); it.hasNext(); ) {
                if (it.next().demandId.equals(demandId)) {
                    it.remove();
                    return;
                }
            }
            throw new IllegalStateException("demand not found");
        }
    }

    public void close() {
        synchronized (mutex) {
            broken = true;
            mutex.notify(); // in some cases ....
        }
    }

    private static class ActiveSubscription {
        String demandId;
        BlockingQueue<CollectedProposal> queue;

        public ActiveSubscription(String demandId, BlockingQueue<CollectedProposal> queue) {
            this.demandId = demandId;
            this.queue = queue;
        }
    }

}