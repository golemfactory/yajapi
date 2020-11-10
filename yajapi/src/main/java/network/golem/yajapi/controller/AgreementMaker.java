package network.golem.yajapi.controller;

import network.golem.yajapi.adapter.ApiInitializer;
import network.golem.yajapi.adapter.SendAdapter;
import network.golem.yajapi.market.models.Proposal;
import network.golem.yajapi.mstrategy.MarketStrategy;
import network.golem.yajapi.reactors.CollectedProposal;
import network.golem.yajapi.reactors.ProposalsCollectingException;
import network.golem.yajapi.reactors.ProposalsCollector;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

//TODO wait for subthread to complete
public class AgreementMaker {
    private static AgreementMaker instance;

    public synchronized static AgreementMaker getInstance() {
        if (instance == null) {
            instance = new AgreementMaker();
        }
        return instance;
    }

    private final Object mutex = new Object();
    private final SendAdapter sendAdapter = SendAdapter.getInstance();
    private final ProposalsCollector proposalsCollector = ProposalsCollector.getInstance();
    private final BlockingQueue<CollectedProposal> proposalsQueue = new LinkedBlockingQueue<>();
    private final List<ActiveDemand> activeDemands = new LinkedList<>();
    private boolean broken = false;

    private AgreementMaker() {
        ApiInitializer.initialize();
        new Thread(() -> {
            processProposals();
        }).start();
        new Thread(() -> {
            checkActiveDemands();
        }).start();
    }

    private void processProposals() {
        CollectedProposal collectedProposal;
        while (true) {
            synchronized (mutex) {
                if (broken) {
                    return;
                }
            }
            try {
                collectedProposal = proposalsQueue.take();
                if (collectedProposal.getDemandId() == null) {  //technical marker of broken
                    return;
                }
            } catch (InterruptedException e) {
                continue;  //could be because of break, in any other case the exception is ignored
            }
            synchronized (mutex) {
                ActiveDemand activeDemand = null;
                for (ActiveDemand activeDemand1 : activeDemands) {
                    if (activeDemand1.demandId.equals(collectedProposal.getDemandId())) {
                        activeDemand = activeDemand1;
                        break;
                    }
                }
                if (activeDemand == null) {
                    try {
                        System.out.println("cannot match the proposal to any active demand ("+collectedProposal.getDemandId()+")"+collectedProposal.getProposal());
                    } catch (ProposalsCollectingException e) {
                        System.out.println("demand "+collectedProposal.getDemandId()+" not active");
                    }
                    continue;
                }
                Proposal proposal;
                try {
                    proposal = collectedProposal.getProposal();
                } catch (ProposalsCollectingException e) {
                    e.printStackTrace();
                    activeDemands.remove(activeDemand);   //TODO to remove  or not
                    proposalsCollector.stopCollecting(activeDemand.demandId); //TODO as above
                    activeDemand.marketStrategy.done();
                    continue;
                }
                long nextCheck = activeDemand.marketStrategy.nextProposal(proposal);
                if (nextCheck == -1) {
                    activeDemands.remove(activeDemand);
                    proposalsCollector.stopCollecting(activeDemand.demandId);
                    activeDemand.marketStrategy.done();   //no need to call done on the market strategy
                    continue;
                }
                activeDemand.nextCheck = nextCheck;
            }
        }
    }

    private void checkActiveDemands() {
        synchronized (mutex) {
            while (true) {
                if (broken) {
                    for (Iterator<ActiveDemand> it = activeDemands.iterator(); it.hasNext(); ) {
                        ActiveDemand activeDemand = it.next();
                        activeDemands.remove(activeDemand);
                        proposalsCollector.stopCollecting(activeDemand.demandId);
                        activeDemand.marketStrategy.done();   //no need to call done on the market strategy
                        it.remove();
                    }
                    return;
                }
                if (activeDemands.size() == 0) {
                    try {
                        mutex.wait();
                    } catch (InterruptedException e) {
                        // exception ignored
                    }
                    continue;
                }
                Collections.sort(activeDemands, (o1, o2) -> Long.compare(o1.nextCheck, o2.nextCheck));
                ActiveDemand activeDemand = activeDemands.get(0);
                long now = System.currentTimeMillis();
                if (now < activeDemand.nextCheck) {
                    try {
                        mutex.wait(activeDemand.nextCheck-now);
                    } catch (InterruptedException e) {
                        // exception ignored
                    }
                    continue;
                }
                long nextCheck = activeDemand.marketStrategy.nextProposal(null);
                if (nextCheck == -1) {
                    activeDemands.remove(activeDemand);
                    proposalsCollector.stopCollecting(activeDemand.demandId);
                    activeDemand.marketStrategy.done();   //no need to call done on the market strategy
                    continue;
                }
                activeDemand.nextCheck = nextCheck;
            }
        }
    }


    public void makeAgreements(String demandId, MarketStrategy marketStrategy) {
        synchronized (mutex) {
            long nextCheck = marketStrategy.nextProposal(null);   //at the beginning the initial nextCheck is set
            if (nextCheck == -1) {
                return;
            }
            proposalsCollector.startCollecting(demandId, proposalsQueue);
            ActiveDemand activeDemand = new ActiveDemand(demandId, marketStrategy, nextCheck);
            activeDemands.add(activeDemand);
            mutex.notify();  //notifies checkActiveDemands()
        }
    }

    public void close() {
        synchronized (mutex) {
            broken = true;
            mutex.notify();   //notifies checkActiveDemands()
            try {
                proposalsQueue.put(new CollectedProposal(null, (Proposal) null));   //just tu trigger queue take()
            } catch (InterruptedException e) {
                // should not happen
                //TODO interrupted exception
            }
        }
    }

    private static class ActiveDemand {
        String demandId;
        MarketStrategy marketStrategy;
        long nextCheck;

        public ActiveDemand(String demandId, MarketStrategy marketStrategy, long nextCheck) {
            this.demandId = demandId;
            this.marketStrategy = marketStrategy;
            this.nextCheck = nextCheck;
        }
    }
}
