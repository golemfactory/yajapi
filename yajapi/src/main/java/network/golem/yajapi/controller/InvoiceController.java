package network.golem.yajapi.controller;

import network.golem.yajapi.payment.models.Invoice;
import network.golem.yajapi.pverifier.PaymentVerifier;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//TODO unregister payment verifiers
//TODO information on not completed tasks
public class InvoiceController {
    private static InvoiceController instance;

    public synchronized static InvoiceController getInstance() {
        if (instance == null) {
            instance = new InvoiceController();
        }
        return instance;
    }

    private final Map<String, ActivityHolder> activityHolders = new HashMap<>();
    private final Map<String, AgreementHolder> agreementHolders = new HashMap<>();
    private final Map<String, DemandHolder> demandHolders = new HashMap<>();
    private final Map<String, PaymentVerifier> paymentVerifiers = new HashMap<>();
    private final Object mutex = new Object();
    private boolean broken = false;

    private InvoiceController() {
    }

    public void acceptedActivities(Invoice invoice) {
        PaymentVerifier paymentVerifier;
        synchronized (mutex) {
            AgreementHolder agreementHolder = agreementHolders.get(invoice.getAgreementId());
            if (agreementHolder == null) {
                System.out.println("agreementHolder is not found: " + invoice.getAgreementId() + ", invoice abandoned: " + invoice.getInvoiceId());
                return;  //TODO what is better?
            }
            paymentVerifier = paymentVerifiers.get(agreementHolder.demandId);
            if (paymentVerifier == null) {
                System.out.println("paymentVerifier is not found, demandId: " + agreementHolder.demandId);
                return; //TODO could we do this better?
            }
        }
        paymentVerifier.acceptedActivities(invoice);
    }

    public void rejectedActivities(Invoice invoice) {
        PaymentVerifier paymentVerifier;
        synchronized (mutex) {
            AgreementHolder agreementHolder = agreementHolders.get(invoice.getAgreementId());
            if (agreementHolder == null) {
                System.out.println("agreementHolder is not found: " + invoice.getAgreementId() + ", invoice abandoned: " + invoice.getInvoiceId());
                return;  //TODO what is better?
            }
            paymentVerifier = paymentVerifiers.get(agreementHolder.demandId);
            if (paymentVerifier == null) {
                System.out.println("paymentVerifier is not found, demandId: " + agreementHolder.demandId);
                return; //TODO could we do this better?
            }
        }
        paymentVerifier.rejectedActivities(invoice);
    }

    public void paidInvoice(Invoice invoice) {
        setStatusForActivities(invoice, SettlementStatus.PAID);
    }

    public void rejectedInvoice(Invoice invoice) {
        setStatusForActivities(invoice, SettlementStatus.REJECTED);
    }

    public void failedInvoice(Invoice invoice) {
        setStatusForActivities(invoice, SettlementStatus.FAILED);
    }

    private void setStatusForActivities(Invoice invoice, SettlementStatus status) {
        if (status == SettlementStatus.UNKNOWN) {
            throw new IllegalArgumentException();
        }
        synchronized (mutex) {
            for (String activityId : invoice.getActivityIds()) {
                ActivityHolder activityHolder = activityHolders.get(activityId); //TODO activity not found
                activityHolder.status = status;
                synchronized (activityHolder) {
                    activityHolder.notifyAll();
                }
            }

            AgreementHolder agreementHolder = agreementHolders.get(invoice.getAgreementId());
            SettlementStatus agreementStatus = SettlementStatus.PAID;
            for (ActivityHolder activityHolder : agreementHolder.activityHolders) {
                if (activityHolder.status == SettlementStatus.UNKNOWN) {
                    return;
                }
                if (activityHolder.status == SettlementStatus.PAID) {
                    continue;
                }
                agreementStatus = SettlementStatus.PARTIALLY_PAID;
            }
            agreementHolder.status = agreementStatus;
            synchronized (agreementHolder) {
                agreementHolder.notifyAll();
            }

            DemandHolder demandHolder = demandHolders.get(agreementHolder.demandId);
            SettlementStatus demandStatus = SettlementStatus.PAID;
            for (AgreementHolder agreementHolder1 : demandHolder.agreementHolders) {  //carefully here, the variable is agreementHolder1 not agreementHolder
                if (agreementHolder1.status == SettlementStatus.UNKNOWN) {
                    return;
                }
                if (agreementHolder1.status == SettlementStatus.PAID) {
                    continue;
                }
                demandStatus = SettlementStatus.PARTIALLY_PAID;
            }
            demandHolder.status = demandStatus;
            synchronized (demandHolder) {
                demandHolder.notifyAll();
            }
        }
    }

    public void waitForActivitySettlement(String activityId) throws InterruptedException {
        ActivityHolder activityHolder;
        synchronized (mutex) {
            if (broken) {
                throw new IllegalStateException("invoice controller already shut down");
            }
            activityHolder = activityHolders.get(activityId); //TODO activity not found
        }
        synchronized (activityHolder) {
            while (true) {
                synchronized (mutex) {
                    if (activityHolder.status != SettlementStatus.UNKNOWN) {
                        return;
                    }
                    if (broken) {
                        return; //TODO do it better
                    }
                }
                activityHolder.wait();
            }
        }
    }

    public void waitForAgreementSettlement(String agreementId) throws InterruptedException {
        AgreementHolder agreementHolder;
        synchronized (mutex) {
            if (broken) {
                throw new IllegalStateException("invoice controller already shut down");
            }
            agreementHolder = agreementHolders.get(agreementId); //TODO agreement not found
        }
        synchronized (agreementHolder) {
            while (true) {
                synchronized (mutex) {
                    if (agreementHolder.status != SettlementStatus.UNKNOWN) {
                        return;
                    }
                    if (broken) {
                        return; //TODO do it better
                    }
                }
                agreementHolder.wait();
            }
        }
    }

    public void waitForDemandSettlement(String demandId) throws InterruptedException {
        DemandHolder demandHolder;
        synchronized (mutex) {
            if (broken) {
                throw new IllegalStateException("invoice controller already shut down");
            }
            demandHolder = demandHolders.get(demandId); //TODO agreement not found
        }
        synchronized (demandHolder) {
            while (true) {
                synchronized (mutex) {
                    if (demandHolder.status != SettlementStatus.UNKNOWN) {
                        return;
                    }
                    if (broken) {
                        return; //TODO do it better
                    }
                }
                demandHolder.wait();
            }
        }
    }

    public void registerActivity(String activityId, String agreementId, String demandId) {
        synchronized (mutex) {
            if (broken) {
                throw new IllegalStateException("invoice controller already shut down");
            }
            ActivityHolder activityHolder = activityHolders.get(activityId);
            if (activityHolder != null) {
                throw new IllegalStateException("activity already registered: " + activityId);
            }

            DemandHolder demandHolder = demandHolders.get(demandId);
            if (demandHolder == null) {
                demandHolder = new DemandHolder(demandId);
                demandHolders.put(demandId, demandHolder);
            }
            demandHolder.status = SettlementStatus.UNKNOWN;
            AgreementHolder agreementHolder = agreementHolders.get(agreementId);
            if (agreementHolder == null) {
                agreementHolder = new AgreementHolder(demandId, agreementId);
                agreementHolders.put(agreementId, agreementHolder);
                demandHolder.agreementHolders.add(agreementHolder);
            }
            agreementHolder.status = SettlementStatus.UNKNOWN;

            activityHolder = new ActivityHolder(agreementId, activityId);
            activityHolders.put(activityId, activityHolder);
            agreementHolder.activityHolders.add(activityHolder);
            activityHolder.status = SettlementStatus.UNKNOWN;
        }
    }

    public void registerPaymentVerifier(PaymentVerifier paymentVerifier, String demandId) {
        synchronized (mutex) {
            if (broken) {
                throw new IllegalStateException("invoice controller already shut down");
            }
            paymentVerifiers.put(demandId, paymentVerifier);
        }
    }

    public void close() {
        synchronized (mutex) {
            if (broken) return;
            broken = true;
            for (ActivityHolder activityHolder : activityHolders.values()) {
                synchronized (activityHolder) {
                    activityHolder.notifyAll();
                }
            }
            for (AgreementHolder agreementHolder : agreementHolders.values()) {
                synchronized (agreementHolder) {
                    agreementHolder.notifyAll();
                }
            }
            for (DemandHolder demandHolder : demandHolders.values()) {
                synchronized (demandHolder) {
                    demandHolder.notifyAll();
                }
            }
        }
    }

    private enum SettlementStatus {
        UNKNOWN, PAID, PARTIALLY_PAID, REJECTED, FAILED
    }

    private static class ActivityHolder {
        String agreementId;
        String activityId;
        SettlementStatus status = SettlementStatus.UNKNOWN;
        ActivityHolder(String agreementId, String activityId) {
            this.agreementId = agreementId;
            this.activityId = activityId;
        }
    }

    private static class AgreementHolder {
        String demandId;
        String agreementId;
        SettlementStatus status = SettlementStatus.UNKNOWN;
        List<ActivityHolder> activityHolders = new LinkedList<>();
        AgreementHolder(String demandId, String agreementId) {
            this.demandId = demandId;
            this.agreementId = agreementId;
        }
    }

    private static class DemandHolder {
        String demandId;
        SettlementStatus status = SettlementStatus.UNKNOWN;
        List<AgreementHolder> agreementHolders = new LinkedList<>();
        DemandHolder(String demandId) {
            this.demandId = demandId;
        }
    }
}
