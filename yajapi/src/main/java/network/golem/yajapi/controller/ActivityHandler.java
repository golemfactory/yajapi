package network.golem.yajapi.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import network.golem.yajapi.activity.models.ExeScriptRequest;
import network.golem.yajapi.adapter.ApiException;
import network.golem.yajapi.adapter.ApiInitializer;
import network.golem.yajapi.adapter.SendAdapter;
import network.golem.yajapi.gftp.GftpService;
import network.golem.yajapi.gftp.GftpServiceException;
import network.golem.yajapi.reactors.ExeScriptResult;
import network.golem.yajapi.reactors.ExeScriptSender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;

/**
 * This is not thread safe.
 */
public class ActivityHandler {
    private final String agreementId;
    private final String activityId;
    private final SendAdapter sendAdapter;
    private final ExeScriptSender exeScriptSender;
    private final GftpService gftp;
    private final ObjectMapper objectMapper;
    private boolean destroyed;
    private List<SingleCommandHolder> commandHolders = new ArrayList<>();

    public ActivityHandler(String agreementId) throws ApiException, IOException {
        ApiInitializer.initialize();
        sendAdapter = SendAdapter.getInstance();
        exeScriptSender = ExeScriptSender.getInstance();
        gftp = GftpService.getInstance();
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.agreementId = agreementId;
        activityId = sendAdapter.createActivity(agreementId);
        destroyed = false;
    }

    public String getAgreementId() {
        return agreementId;
    }

    public String getActivityId() {
        return activityId;
    }

    public ActivityHandler deploy() {
        if (destroyed) {
            throw new IllegalStateException();
        }
        commandHolders.add(new SingleCommandHolder().deploy(new Deploy()));
        return this;
    }

    public ActivityHandler start() {
        if (destroyed) {
            throw new IllegalStateException();
        }
        commandHolders.add(new SingleCommandHolder().start(new Start()));
        return this;
    }

    public ActivityHandler transferTo(String srcFile, String destFile) throws InterruptedException, GftpServiceException {
        if (destroyed) {
            throw new IllegalStateException();
        }
        String srcUrl = gftp.publish(srcFile);
        String destUrl = "container:"+destFile;
        commandHolders.add(new SingleCommandHolder().transfer(new Transfer().from(srcUrl).to(destFile)));
        return this;
    }

    public ActivityHandler run(String entryPoint, String[] args) {
        if (destroyed) {
            throw new IllegalStateException();
        }
        commandHolders.add(new SingleCommandHolder().run(new Run().entryPoint(entryPoint).args(args)));
        return this;
    }

    public ActivityHandler transferFrom(String srcFile, String destFile) throws InterruptedException, GftpServiceException {
        if (destroyed) {
            throw new IllegalStateException();
        }
        String srcUrl = "container:"+srcFile;
        String destUrl = gftp.receive(destFile);
        commandHolders.add(new SingleCommandHolder().transfer(new Transfer().from(srcUrl).to(destUrl)));
        return this;
    }

    public Future<ExeScriptResult> exec(float timeout) throws ApiException {
        String text = null;
        try {
            text = objectMapper.writeValueAsString(commandHolders);
            System.out.println(text);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("unexpected", e);
        }
        commandHolders = new ArrayList<>();
        return exeScriptSender.sendExeScript(new ExeScriptRequest().text(text), activityId, timeout);
    }

    public String exec(float timeout, BlockingQueue<ExeScriptResult> queue) throws ApiException {
        String text = null;
        try {
            text = objectMapper.writeValueAsString(commandHolders);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("unexpected", e);
        }
        commandHolders = new ArrayList<>();
        return exeScriptSender.sendExeScript(new ExeScriptRequest().text(text), activityId, timeout, queue);
    }

    public void terminate() throws ApiException {
        if (destroyed) {
            throw new IllegalStateException();
        }
        sendAdapter.exec(new ExeScriptRequest().text("[{\"terminate\":{}}]"), activityId);  //this command generates no answer
        sendAdapter.destroyActivity(activityId);
        destroyed = true;
    }

    private static class SingleCommandHolder {
        @JsonProperty("deploy")
        Deploy deploy;
        @JsonProperty("start")
        Start start;
        @JsonProperty("transfer")
        Transfer transfer;
        @JsonProperty("run")
        Run run;
        SingleCommandHolder deploy(Deploy deploy) {this.deploy = deploy; return this;}
        SingleCommandHolder start(Start start) {this.start = start; return this;}
        SingleCommandHolder transfer(Transfer transfer) {this.transfer = transfer; return this;}
        SingleCommandHolder run(Run run) {this.run = run; return this;}
    }

    private static class Deploy {

    }

    private static class Start {

    }

    private static class Transfer {
        @JsonProperty("from")
        String from;
        @JsonProperty("to")
        String to;
        Transfer from(String from) {this.from = from; return this;}
        Transfer to(String to) {this.to = to; return this;}
    }

    private static class Run {
        @JsonProperty("entry_point")
        String entry_point;
        @JsonProperty("args")
        String[] args;
        Run entryPoint(String entry_point) {this.entry_point = entry_point; return this;}
        Run args(String[] args) {this.args = args; return this;}
    }

}
