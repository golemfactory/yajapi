package network.golem.yajapi.gftp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GftpService {
    private static GftpService instance;
    public static synchronized GftpService getInstance() throws IOException {
        if (instance == null) {
            instance = new GftpService();
            instance.init();
        }
        return instance;
    }
    private GftpService() {

    }

    private boolean broken = false;
    private Process gftpProcess;
    private PrintStream commandPrinter;
    private int id = 0;
    private final Object mutex = new Object();
    private final Map<Integer, ResultHolder> results = new HashMap<>();

    private enum CommandType {
        PUBLISH("{\"jsonrpc\": \"2.0\", \"id\": \"%d\", \"method\": \"publish\", \"params\": {\"files\": [\"%s\"]}}"){
            @Override
            String getUrl(Object result) {
                Map singleResult = (Map) ((List)result).get(0);
                return  (String) singleResult.get("url");
            }
        },
        RECEIVE("{\"jsonrpc\": \"2.0\", \"id\": \"%d\", \"method\": \"receive\", \"params\": {\"output_file\": \"%s\"}}"){
            @Override
            String getUrl(Object result) {
                return (String) ((Map)result).get("url");
            }
        };

        private final String commandPattern;
        CommandType(String commandPattern) {
            this.commandPattern = commandPattern;
        }

        abstract String getUrl(Object result);
        String formatCommand(Integer id, String file) {
            return String.format(commandPattern, id, file);
        }
    }

    private static class ResultHolder {
        CommandType commandType;  //only input
        String url;   //url returned by gftp
        Exception exc;   //probably gftp answer is invalid
        boolean cancelled;  //it means that gftp process is broken
    }

    public String publish(String file) throws GftpServiceException, InterruptedException {
        return command(file, CommandType.PUBLISH);
    }

    public String receive(String file) throws GftpServiceException, InterruptedException {
        return command(file, CommandType.RECEIVE);
    }

    public String command(String file, CommandType commandType) throws GftpServiceException, InterruptedException {
        ResultHolder resultHolder = new ResultHolder();
        resultHolder.commandType = commandType;
        Integer id;
        synchronized (mutex) {
            if (broken) {
                throw new GftpServiceException("service permanently broken");
            }
            id = ++this.id;
            results.put(id, resultHolder);
            String command = commandType.formatCommand(id, file);
            commandPrinter.println(command);  //TODO no exception?
        }
        synchronized (resultHolder) {
            try {
                if (resultHolder.url == null && resultHolder.exc == null && !resultHolder.cancelled) {
                    resultHolder.wait();
                }
            } finally {
                synchronized (mutex) {
                    results.remove(id);
                }
            }
        }
        if (resultHolder.cancelled) {  //the gftp process is broken
            throw new GftpServiceException("gftp service is down");
        }
        if (resultHolder.exc != null) {
            throw new GftpServiceException(resultHolder.exc);
        }
        return resultHolder.url;
    }

    private void answer(String stringAnswer) {
        synchronized (mutex) {
            Map entireAnswer;
            try {
                entireAnswer = new ObjectMapper().readValue(stringAnswer, HashMap.class);
            } catch (JsonProcessingException | RuntimeException e) {
                e.printStackTrace();
                clean();
                return;
            }
            Object result = entireAnswer.get("result");
            String idString = (String) entireAnswer.get("id");
            Integer id = Integer.parseInt(idString);
            ResultHolder resultHolder = results.get(id);
            if (resultHolder == null) {
                System.err.println("unrecognized command id: "+id);
                return;
            }
            synchronized (resultHolder) {
                resultHolder.url = resultHolder.commandType.getUrl(result);
                resultHolder.notifyAll();
            }
        }
    }

    private void init() throws IOException {
        try {
            gftpProcess = Runtime.getRuntime().exec(new String[]{"gftp", "server"});
            commandPrinter = new PrintStream(gftpProcess.getOutputStream(), true);
        } catch (IOException e) {
            broken = true;  //the results map is empty
            throw e;
        }
        new Thread() {
            @Override
            public void run() {
                BufferedReader reader = new BufferedReader(new InputStreamReader(gftpProcess.getInputStream()));
                String line;
                try {
                    while ((line = reader.readLine()) != null) {
                        answer(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                clean();
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                byte[] buf = new byte[8192];
                int c;
                InputStream errorStream = gftpProcess.getErrorStream();
                try {
                    while (true) {
                        c = errorStream.read(buf);
                        if (c == -1) {
                            break;
                        }
                        System.err.write(buf, 0, c);
                        System.err.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                clean();
            }
        }.start();
    }

    private void clean() {
        synchronized (mutex) {
            if (broken) {
                return;
            }
            broken = true;
            for (ResultHolder resultHolder : results.values()) {
                if (resultHolder.url != null || resultHolder.exc != null) {
                    continue;
                }
                synchronized (resultHolder) {
                    resultHolder.cancelled = true;
                    resultHolder.notifyAll();
                }
            }
            gftpProcess.destroy();
        }
    }

    public void close() {
        clean();
    }

}
