package network.golem.yajapi.reactors;

import network.golem.yajapi.adapter.ApiInitializer;
import network.golem.yajapi.controller.InvoiceController;
import network.golem.yajapi.payment.ApiException;
import network.golem.yajapi.payment.apis.RequestorApi;
import network.golem.yajapi.payment.models.Invoice;
import network.golem.yajapi.payment.models.InvoiceEvent;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//TODO what if activityId is not unique, instead (agreementId,activityId) is unique?
//TODO information on not completed tasks
//TODO wait for subthread to complete
public class InvoiceCollector {
    private static InvoiceCollector instance;

    public synchronized static InvoiceCollector getInstance() {
        if (instance == null) {
            instance = new InvoiceCollector();
        }
        return instance;
    }

    private final InvoiceController invoiceController = InvoiceController.getInstance();
    private final RequestorApi paymentRequestorApi = ApiInitializer.newPaymentRequestorApi();

    private InvoiceCollector() {
        new Thread(() -> {
            checkForInvoices();
        }).start();
    }

    private final Object mutex = new Object();
    private final List<AwaitingReceivedInvoice> awaitingReceivedInvoices = new LinkedList<>();
    private final List<String> awaitingAcceptedActivities = new LinkedList<>();
    private final List<String> awaitingRejectedActivities = new LinkedList<>();
    private boolean broken = false;

    private void checkForInvoices() {
        OffsetDateTime invoiceLastCheck = OffsetDateTime.now();
        while (true) {
            synchronized (mutex) {
                if (broken) {
                    return;
                }
                if (awaitingAcceptedActivities.size()+awaitingRejectedActivities.size() == 0){  //if there is no expected invoices, we check slower
                    try {
                        mutex.wait(10000);
                    } catch (InterruptedException e) {
                        //we ignore this exception
                    }
                    if (broken) {
                        return;
                    }
                }
            }
            OffsetDateTime nextLastInvoiceCheck = OffsetDateTime.now();
            try {
                List<InvoiceEvent> invoiceEvents = paymentRequestorApi.getRequestorInvoiceEvents(BigDecimal.valueOf(1L), invoiceLastCheck);
                for (InvoiceEvent invoiceEvent : invoiceEvents) {
                    switch (invoiceEvent.getEventType()) {
                        case RECEIVED:
                            receivedInvoice(invoiceEvent);
                            break;
                        default:
                            System.out.println("invoiceId: "+invoiceEvent.getInvoiceId()+" is "+invoiceEvent.getEventType());
                    }
                }
            } catch (ApiException e) {
                e.printStackTrace();
            }
            invoiceLastCheck = nextLastInvoiceCheck;
        }
    }

    private void receivedInvoice(InvoiceEvent invoiceEvent) {
        synchronized (mutex) {
            for (AwaitingReceivedInvoice awaitingReceivedInvoice : awaitingReceivedInvoices) {
                if (awaitingReceivedInvoice.invoice.getInvoiceId().equals(invoiceEvent.getInvoiceId())) {
                    return; //double invoice
                }
            }
            Invoice invoice = null;
            try {
                invoice = paymentRequestorApi.getReceivedInvoice(invoiceEvent.getInvoiceId());
            } catch (ApiException e) {
                System.out.println("could not retrieve the invoice "+invoiceEvent.getInvoiceId());
                e.printStackTrace();
                return;
            }
            AwaitingReceivedInvoice awaitingReceivedInvoice = new AwaitingReceivedInvoice().invoice(invoice).timestamp(invoiceEvent.getTimestamp());
            for (Iterator<String> it = awaitingAcceptedActivities.iterator(); it.hasNext(); ) {
                String activityId = it.next();
                if (invoice.getActivityIds().contains(activityId)) {
                    awaitingReceivedInvoice.acceptedActivities.add(activityId);
                    it.remove();
                }
            }
            for (Iterator<String> it = awaitingRejectedActivities.iterator(); it.hasNext(); ) {
                String activityId = it.next();
                if (invoice.getActivityIds().contains(activityId)) {
                    awaitingReceivedInvoice.rejectedActivities.add(activityId);
                    it.remove();
                }
            }
            if (invoice.getActivityIds().size() > awaitingReceivedInvoice.acceptedActivities.size() + awaitingReceivedInvoice.rejectedActivities.size()) {
                awaitingReceivedInvoices.add(awaitingReceivedInvoice);
            } else {
                if (awaitingReceivedInvoice.rejectedActivities.size() > 0) {
                    invoiceController.rejectedActivities(invoice);
                } else {
                    invoiceController.acceptedActivities(invoice);
                }
            }
        }
    }

    public void acceptedActivity(String activityId) {
        synchronized (mutex) {
            if (broken) {
                throw new IllegalStateException("invoice collector already shut down");
            }
            if (checkIfActivityRegistered(activityId)) {
                throw new IllegalStateException(activityId);
            }
            for (Iterator<AwaitingReceivedInvoice> it = awaitingReceivedInvoices.iterator(); it.hasNext(); ) {
                AwaitingReceivedInvoice awaitingReceivedInvoice = it.next();
                if (awaitingReceivedInvoice.invoice.getActivityIds().contains(activityId)) {
                    awaitingReceivedInvoice.acceptedActivities.add(activityId);
                    if (awaitingReceivedInvoice.invoice.getActivityIds().size() == awaitingReceivedInvoice.acceptedActivities.size() + awaitingReceivedInvoice.rejectedActivities.size()) {
                        if (awaitingReceivedInvoice.rejectedActivities.size() > 0) {
                            invoiceController.rejectedActivities(awaitingReceivedInvoice.invoice);
                        } else {
                            invoiceController.acceptedActivities(awaitingReceivedInvoice.invoice);
                        }
                        it.remove();
                    }
                    return;  //no need to notify
                }
            }
            awaitingAcceptedActivities.add(activityId);
            mutex.notify(); //in case the collecting thread is on long wait
        }
    }

    public void rejectedActivity(String activityId) {
        synchronized (mutex) {
            if (broken) {
                throw new IllegalStateException("invoice collector already shutdown");
            }
            if (checkIfActivityRegistered(activityId)) {
                throw new IllegalStateException(activityId);
            }
            for (Iterator<AwaitingReceivedInvoice> it = awaitingReceivedInvoices.iterator(); it.hasNext(); ) {
                AwaitingReceivedInvoice awaitingReceivedInvoice = it.next();
                if (awaitingReceivedInvoice.invoice.getActivityIds().contains(activityId)) {
                    awaitingReceivedInvoice.rejectedActivities.add(activityId);
                    if (awaitingReceivedInvoice.invoice.getActivityIds().size() == awaitingReceivedInvoice.acceptedActivities.size() + awaitingReceivedInvoice.rejectedActivities.size()) {
                        if (awaitingReceivedInvoice.rejectedActivities.size() > 0) {
                            invoiceController.rejectedActivities(awaitingReceivedInvoice.invoice);
                        } else {
                            invoiceController.acceptedActivities(awaitingReceivedInvoice.invoice);
                        }
                        it.remove();
                    }
                    return;  //no need to notify
                }
            }
            awaitingRejectedActivities.add(activityId);
            mutex.notify(); //in case the collecting thread is on long wait
        }
    }

    private boolean checkIfActivityRegistered(String activityId) {
        if (awaitingAcceptedActivities.contains(activityId)) return true;
        if (awaitingRejectedActivities.contains(activityId)) return true;
        for (AwaitingReceivedInvoice awaitingReceivedInvoice : awaitingReceivedInvoices) {
            if (awaitingReceivedInvoice.acceptedActivities.contains(activityId)) return true;
            if (awaitingReceivedInvoice.rejectedActivities.contains(activityId)) return true;
        }
        return false;
    }

    private static class AwaitingReceivedInvoice {
        Invoice invoice;
        OffsetDateTime timestamp;
        List<String> acceptedActivities = new LinkedList<>();
        List<String> rejectedActivities = new LinkedList<>();
        AwaitingReceivedInvoice invoice(Invoice invoice) {this.invoice = invoice; return this;}
        AwaitingReceivedInvoice timestamp(OffsetDateTime timestamp) {this.timestamp = timestamp; return this;}
    }

    public void close() {
        synchronized (mutex) {
            if (broken) return;
            broken = true;
            mutex.notify(); //there is only one thread to notify
        }
    }
}
