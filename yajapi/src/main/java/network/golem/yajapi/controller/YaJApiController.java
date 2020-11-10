package network.golem.yajapi.controller;

import network.golem.yajapi.adapter.ApiException;
import network.golem.yajapi.adapter.SendAdapter;
import network.golem.yajapi.gftp.GftpService;
import network.golem.yajapi.market.models.Demand;
import network.golem.yajapi.mstrategy.MarketStrategy;
import network.golem.yajapi.payment.models.Account;
import network.golem.yajapi.payment.models.Allocation;
import network.golem.yajapi.pverifier.PaymentVerifier;
import network.golem.yajapi.reactors.ExeScriptSender;
import network.golem.yajapi.reactors.InvoiceCollector;
import network.golem.yajapi.reactors.ProposalsCollector;

import java.io.IOException;
import java.util.List;

public class YaJApiController {
    private static YaJApiController instance;

    public synchronized static YaJApiController getInstance() {
        if (instance == null) {
            instance = new YaJApiController();
        }
        if (instance.destroyed) {
            return null; //TODO do better
        }
        return instance;
    }

    private GftpService gftp = null;
    private SendAdapter sendAdapter = null;
    private ExeScriptSender exeScriptSender = null;
    private ProposalsCollector proposalsCollector = null;
    private InvoiceController invoiceController = null;
    private InvoiceCollector invoiceCollector = null;
    private AgreementMaker agreementMaker = null;
    private boolean destroyed = false;

    private YaJApiController() {
        try {
            gftp = GftpService.getInstance();
            sendAdapter = SendAdapter.getInstance();
            exeScriptSender = ExeScriptSender.getInstance();
            proposalsCollector = ProposalsCollector.getInstance();
            invoiceController = InvoiceController.getInstance();
            invoiceCollector = InvoiceCollector.getInstance();
            agreementMaker = AgreementMaker.getInstance();
        } catch (Exception e) {
            destroy();
        }
    }

    public void destroy() {
        destroyed = true;
        exeScriptSender.close();
        proposalsCollector.close();
        invoiceController.close();
        invoiceCollector.close();
        gftp.close();
        agreementMaker.close();
        //TODO catching exception
        //TODO synchronization
    }

    //------------------------------ sendAdapter

    public List<Account> getSendAccounts() throws ApiException {  //TODO all methods disable on destroy
        return sendAdapter.getSendAccounts();
    }

    public Allocation createAllocation(Allocation allocation) throws ApiException {
        return sendAdapter.createAllocation(allocation);
    }

    public void unsubscribeDemand(String demandId) throws ApiException {
        sendAdapter.unsubscribeDemand(demandId);
    }

    public void releaseAllocation(String allocationId) throws ApiException {
        sendAdapter.releaseAllocation(allocationId);
    }

    public void terminateAgreement(String agreementId) throws ApiException {
        sendAdapter.terminateAgreement(agreementId);
    }

    //----------------------------- invoiceCollector

    public void acceptedActivity(String activityId) {
        invoiceCollector.acceptedActivity(activityId);
    }

    public void rejectedActivity(String activityId) {
        invoiceCollector.rejectedActivity(activityId);
    }

    //----------------------------- invoiceController

    public void waitForActivitySettlement(String activityId) throws InterruptedException {
        invoiceController.waitForActivitySettlement(activityId);
    }

    public void waitForAgreementSettlement(String agreementId) throws InterruptedException {
        invoiceController.waitForAgreementSettlement(agreementId);
    }

    public void waitForDemandSettlement(String demandId) throws InterruptedException {
        invoiceController.waitForDemandSettlement(demandId);
    }

    //----------------------------- others

    public String createDemand(Demand demand, MarketStrategy marketStrategy, PaymentVerifier paymentVerifier) throws ApiException {
        String demandId = sendAdapter.subscribeDemand(demand);
        marketStrategy.setDemandId(demandId);
        paymentVerifier.setDemandId(demandId);
        invoiceController.registerPaymentVerifier(paymentVerifier, demandId);
        agreementMaker.makeAgreements(demandId, marketStrategy);
        return demandId;
    }

    public ActivityHandler createActivity(String demandId, String agreementId) throws IOException, ApiException {
        ActivityHandler activityHandler = new ActivityHandler(agreementId);
        invoiceController.registerActivity(activityHandler.getActivityId(), agreementId, demandId);
        return activityHandler;
    }

}
