package network.golem.yajapi.mstrategy;

import network.golem.yajapi.market.models.Proposal;

public interface MarketStrategy {
    long nextProposal(Proposal proposal);
    void done();

    /**
     * invoked immediately after demand subscription by yajapi controller
     * @param demandId
     */
    void setDemandId(String demandId);
}
