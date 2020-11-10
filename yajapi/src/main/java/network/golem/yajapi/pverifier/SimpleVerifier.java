package network.golem.yajapi.pverifier;

import network.golem.yajapi.adapter.ApiException;
import network.golem.yajapi.adapter.SendAdapter;
import network.golem.yajapi.controller.InvoiceController;
import network.golem.yajapi.payment.models.*;

public class SimpleVerifier implements PaymentVerifier {
    private final Allocation allocation;
    private String demandId;
    private final InvoiceController invoiceController;
    private final SendAdapter sendAdapter;

    public SimpleVerifier(Allocation allocation) {
        this.allocation = allocation;
        this.invoiceController = InvoiceController.getInstance();
        this.sendAdapter = SendAdapter.getInstance();
    }

    @Override
    public void acceptedActivities(Invoice invoice) {
        try {
            sendAdapter.acceptInvoice(new Acceptance().allocationId(allocation.getAllocationId()).totalAmountAccepted(invoice.getAmount()), invoice.getInvoiceId(), null);
            System.out.println("paid: "+invoice);
        } catch (ApiException e) {
            System.out.println("invoice payment failed: "+invoice.getInvoiceId());
            e.printStackTrace();
            invoiceController.failedInvoice(invoice);
            return;
        }
        invoiceController.paidInvoice(invoice);
    }

    @Override
    public void rejectedActivities(Invoice invoice) {
        try {
            sendAdapter.rejectInvoice(new Rejection().rejectionReason(RejectionReason.BAD_SERVICE).message("activities' results rejected"), invoice.getInvoiceId(), null);
        } catch (ApiException e) {
            System.out.println("invoice rejection failed: "+invoice.getInvoiceId());
            e.printStackTrace();
            invoiceController.failedInvoice(invoice);
            return;
        }
        invoiceController.rejectedInvoice(invoice);
    }

    @Override
    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }
}
