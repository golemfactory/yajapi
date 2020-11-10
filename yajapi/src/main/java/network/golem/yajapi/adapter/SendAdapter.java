package network.golem.yajapi.adapter;

import network.golem.yajapi.activity.models.ExeScriptRequest;
import network.golem.yajapi.market.models.Agreement;
import network.golem.yajapi.market.models.AgreementProposal;
import network.golem.yajapi.market.models.Demand;
import network.golem.yajapi.market.models.Proposal;
import network.golem.yajapi.payment.models.Acceptance;
import network.golem.yajapi.payment.models.Account;
import network.golem.yajapi.payment.models.Allocation;
import network.golem.yajapi.payment.models.Rejection;

import java.math.BigDecimal;
import java.util.List;

public class SendAdapter {
    private static SendAdapter instance;

    public static synchronized SendAdapter getInstance() {
        if (instance != null)
            return instance;
        instance = new SendAdapter();
        return instance;
    }

    private final network.golem.yajapi.payment.apis.RequestorApi paymentRequestorApi = new network.golem.yajapi.payment.apis.RequestorApi();
    private final network.golem.yajapi.activity.apis.RequestorStateApi activityRequestorStateApi = new network.golem.yajapi.activity.apis.RequestorStateApi();
    private final network.golem.yajapi.activity.apis.RequestorControlApi activityRequestorControlApi = new network.golem.yajapi.activity.apis.RequestorControlApi();
    private final network.golem.yajapi.market.apis.RequestorApi marketRequestorApi = new network.golem.yajapi.market.apis.RequestorApi();

    private SendAdapter() {
        ApiInitializer.initialize();
    }

    public synchronized List<Account> getSendAccounts() throws ApiException {
        try {
            return paymentRequestorApi.getSendAccounts();
        } catch (network.golem.yajapi.payment.ApiException e) {
            throw new ApiException(e);
        }
    }

    public synchronized Allocation createAllocation(Allocation allocation) throws ApiException {
        try {
            return paymentRequestorApi.createAllocation(allocation);
        } catch (network.golem.yajapi.payment.ApiException e) {
            throw new ApiException(e);
        }
    }

    public synchronized String subscribeDemand(Demand demand) throws ApiException {
        try {
            String demandId = marketRequestorApi.subscribeDemand(demand);
            if (demandId.charAt(0) == '"') {    //nie wiem dlaczego ale Yagna dodaje ciapki
                demandId = demandId.substring(1, demandId.length() - 1);
            }
            return demandId;
        } catch (network.golem.yajapi.market.ApiException e) {
            throw new ApiException(e);
        }
    }

    public synchronized String createAgreement(AgreementProposal agreementProposal) throws ApiException {
        try {
            String agreementId = marketRequestorApi.createAgreement(agreementProposal);
            if (agreementId.charAt(0) == '"') {    //nie wiem dlaczego ale Yagna dodaje ciapki
                agreementId = agreementId.substring(1, agreementId.length() - 1);
            }
            return agreementId;
        } catch (network.golem.yajapi.market.ApiException e) {
            throw new ApiException(e);
        }
    }

    public synchronized Agreement getAgreement(String agreementId) throws ApiException {
        try {
            return marketRequestorApi.getAgreement(agreementId);
        } catch (network.golem.yajapi.market.ApiException e) {
            throw new ApiException(e);
        }
    }

    public synchronized void confirmAgreement(String agreementId) throws ApiException {
        try {
            marketRequestorApi.confirmAgreement(agreementId);
        } catch (network.golem.yajapi.market.ApiException e) {
            throw new ApiException(e);
        }
    }

    public synchronized Proposal getProposalOffer(String demandId, String proposalId) throws ApiException {
        try {
            return marketRequestorApi.getProposalOffer(demandId, proposalId);
        } catch (network.golem.yajapi.market.ApiException e) {
            throw new ApiException(e);
        }
    }

    public synchronized String counterProposalDemand(Proposal counterProposal, String demandId, String proposalId) throws ApiException {
        try {
            String counterProposalId = marketRequestorApi.counterProposalDemand(counterProposal, demandId, proposalId);
            if (counterProposalId.charAt(0) == '"') {    //nie wiem dlaczego ale Yagna dodaje ciapki
                counterProposalId = counterProposalId.substring(1, counterProposalId.length() - 1);
            }
            return counterProposalId;
        } catch (network.golem.yajapi.market.ApiException e) {
            throw new ApiException(e);
        }
    }

    public synchronized String createActivity(String agreementId) throws ApiException {
        try {
            String activityId = activityRequestorControlApi.createActivity('"' + agreementId + '"');
            if (activityId.charAt(0) == '"') {
                activityId = activityId.substring(1, activityId.length() - 1);
            }
            return activityId;
        } catch (network.golem.yajapi.activity.ApiException e) {
            throw new ApiException(e);
        }
    }

    public synchronized void destroyActivity(String activityId) throws ApiException {
        try {
            activityRequestorControlApi.destroyActivity(activityId);
        } catch (network.golem.yajapi.activity.ApiException e) {
            throw new ApiException(e);
        }
    }

    public synchronized String exec(ExeScriptRequest script, String activityId) throws ApiException {
        try {
            String batchId = activityRequestorControlApi.exec(script, activityId);
            if (batchId.charAt(0) == '"') {
                batchId = batchId.substring(1, batchId.length()-1);
            }
            return batchId;
        } catch (network.golem.yajapi.activity.ApiException e) {
            throw new ApiException(e);
        }
    }

    public synchronized void unsubscribeDemand(String demandId) throws ApiException {
        try {
            marketRequestorApi.unsubscribeDemand(demandId);
        } catch (network.golem.yajapi.market.ApiException e) {
            throw new ApiException(e);
        }
    }

    public synchronized void releaseAllocation(String allocationId) throws ApiException {
        try {
            paymentRequestorApi.releaseAllocation(allocationId);
        } catch (network.golem.yajapi.payment.ApiException e) {
            throw new ApiException(e);
        }
    }

    public synchronized void acceptInvoice(Acceptance acceptance, String invoiceId, BigDecimal timeout) throws ApiException {
        try {
            paymentRequestorApi.acceptInvoice(acceptance, invoiceId, timeout);
        } catch (network.golem.yajapi.payment.ApiException e) {
            throw new ApiException(e);
        }
    }

    public void rejectInvoice(Rejection rejection, String invoiceId, BigDecimal timeout) throws ApiException {
        try {
            paymentRequestorApi.rejectInvoice(rejection, invoiceId, timeout);
        } catch (network.golem.yajapi.payment.ApiException e) {
            throw new ApiException(e);
        }
    }

    public void terminateAgreement(String agreementId) throws ApiException {
        try {
            marketRequestorApi.terminateAgreement(agreementId);
        } catch (network.golem.yajapi.market.ApiException e) {
            throw new ApiException(e);
        }
    }
}
