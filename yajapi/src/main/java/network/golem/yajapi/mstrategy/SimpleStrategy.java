package network.golem.yajapi.mstrategy;

import network.golem.yajapi.adapter.ApiException;
import network.golem.yajapi.adapter.ApiInitializer;
import network.golem.yajapi.adapter.SendAdapter;
import network.golem.yajapi.market.apis.RequestorApi;
import network.golem.yajapi.market.models.Agreement;
import network.golem.yajapi.market.models.AgreementProposal;
import network.golem.yajapi.market.models.Proposal;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleStrategy implements MarketStrategy {
    private String demandId;
    private final SendAdapter sendAdapter;
    private final RequestorApi marketRequestorApi;
    private final HashMap<String, Object> properties;
    private final String constraints;
    private final int agreementsCount;
    private final long deadline;
    private final List<Agreement> agreements = new ArrayList<>();
    private final Object mutex = new Object();
    private boolean done = false;

    public SimpleStrategy(HashMap<String, Object> properties, String constraints, int agreementsCount, float timeout) {
        this.sendAdapter = SendAdapter.getInstance();
        this.marketRequestorApi = ApiInitializer.newMarketRequestorApi();
        this.properties = properties;
        this.constraints = constraints;
        this.agreementsCount = agreementsCount;
        this.deadline = System.currentTimeMillis()+(long)(1000*timeout);
    }

    @Override
    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    @Override
    public long nextProposal(Proposal proposal) {
        synchronized (mutex) {
            if (done) {
                return -1;
            }
            if (System.currentTimeMillis() > deadline) {
                done = true;
                mutex.notifyAll();
                return -1;
            }
            if (proposal == null) {
                return deadline;
            }
            if (!proposal.getState().equals(Proposal.StateEnum.DRAFT)) {
                try {
                    String counterProposalId = sendAdapter.counterProposalDemand((Proposal) new Proposal().properties(properties).constraints(constraints), demandId, proposal.getProposalId());
                    System.out.println("counter proposal");
                } catch (Exception e) {
                    System.out.println("answering to proposal " + proposal.getProposalId() + " failed");
                    System.out.println(proposal);
                    e.printStackTrace();
                }
                return deadline;
            }
            String agreementId;
            try {
                agreementId = sendAdapter.createAgreement(new AgreementProposal().proposalId(proposal.getProposalId()).validTo(OffsetDateTime.now().plusMinutes(3)));
                sendAdapter.confirmAgreement(agreementId);
                String approval = marketRequestorApi.waitForApproval(agreementId, 10f);
                System.out.println(approval);
                if (approval == null) {
                    System.out.println("agreement "+agreementId+" abandoned");
                    return deadline;
                }
                if (!approval.equals("\"Approved\"")) {
                    System.out.println("agreement rejected "+agreementId);
                    return deadline;
                }
            } catch (ApiException | network.golem.yajapi.market.ApiException e) {
                e.printStackTrace();
                System.out.println("agreement abandoned, for proposal id: "+proposal.getProposalId());
                return deadline;
            }
            try {
                Agreement agreement = sendAdapter.getAgreement(agreementId);
                agreements.add(agreement);
            } catch (ApiException e) {
                e.printStackTrace();
                System.out.println("something went terribly wrong, agreement abandoned "+agreementId);
                return deadline;
            }
            if (agreements.size() >= agreementsCount) {
                done = true;
                mutex.notifyAll();
                return -1;
            } else {
                return deadline;
            }
        }
    }

    @Override
    public void done() {
        synchronized (mutex) {
            done = true;
            mutex.notifyAll();
        }
    }

    public List<Agreement> get() throws InterruptedException {  //TODO what to do with this exception
        synchronized (mutex) {
            while (!done) {
                mutex.wait();
            }
            return agreements;
        }
    }
}
