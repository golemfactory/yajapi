package network.golem.yajapi.pverifier;

import network.golem.yajapi.payment.models.Invoice;

/**
 * verifies whether the invoice amount is acceptable and pays(!) the invoice
 * it is mandatory to call InvoiceController after payment or rejection
 */
public interface PaymentVerifier {
    public void acceptedActivities(Invoice invoice);
    public void rejectedActivities(Invoice invoice);
    public void setDemandId(String demandId);
}
