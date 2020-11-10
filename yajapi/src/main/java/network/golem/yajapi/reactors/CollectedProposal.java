package network.golem.yajapi.reactors;

import network.golem.yajapi.market.models.Proposal;

public class CollectedProposal {
    private String demandId;
    private Proposal proposal;
    private ProposalsCollectingException exc;

    public CollectedProposal(String demandId, Proposal proposal) {
        this.demandId = demandId;
        this.proposal = proposal;
    }

    public CollectedProposal(String demandId, ProposalsCollectingException exc) {
        this.demandId = demandId;
        this.exc = exc;
    }

    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    public Proposal getProposal() throws ProposalsCollectingException {
        if (exc != null) {
            throw exc;
        }
        return proposal;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }
}
