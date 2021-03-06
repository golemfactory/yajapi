/*
 * Yagna Payment API
 *  Invoicing and Payments is a fundamental area of Yagna Ecosystem functionality. It includes aspects of communication between Requestor, Provider and a selected Payment Platform, which becomes crucial when Activities are executed in the context of negotiated Agreements. Yagna applications must be able to exercise various payment models, and the Invoicing/Payment-related communication is happening in parallel to Activity control communication. To define functional patterns of Requestor/Provider interaction in this area, Payment API is specified.  An important principle of the Yagna Payment API is that the actual payment transactions are hidden behind the Invoice flow. In other words, a Yagna Application on Requestor side isn’t expected to trigger actual payment transactions. Instead it is expected to receive and accept Invoices raised by the Provider - based on Application’s Invoice Accept notifications, the Payment API implementation orchestrates the payment via a configured Payment platform.  **NOTE: This specification is work-in-progress.** 
 *
 * OpenAPI spec version: 1.2.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package network.golem.yajapi.payment.models;

import java.util.Objects;
import java.util.Arrays;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Accepted status indicates that the Requestor confirms the Amount/Total Amount Due on the Invoice/Debit Note, respectively. The Payment API Implementation is expected to proceed with the orchestration of the payment. Internals of the payment processing (eg. payment processing internal states) are specific to the selected Payment Platform, and must be indicated as an attribute of the Accepted status. However, as they are specific - they shall not be standardized by the Payment API.  A Rejected Invoice/Debit Note can subsequently be Accepted.  An Accepted Invoice/Debit Note cannot be subsequently Rejected.  There is a difference between Paid and Settled - depending on a Payment Platform. Paid indicates that the Requestor has ordered Payments of Total Amount Due as indicated by received/accepted Debit Notes/Invoice. Settled indicates that the Provider has reliably received the Payments. 
 */
public enum InvoiceStatus {
  ISSUED("ISSUED"),
  RECEIVED("RECEIVED"),
  ACCEPTED("ACCEPTED"),
  REJECTED("REJECTED"),
  FAILED("FAILED"),
  SETTLED("SETTLED"),
  CANCELLED("CANCELLED");

  private String value;

  InvoiceStatus(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static InvoiceStatus fromValue(String text) {
    for (InvoiceStatus b : InvoiceStatus.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
