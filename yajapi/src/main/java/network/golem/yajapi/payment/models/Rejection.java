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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import network.golem.yajapi.payment.models.RejectionReason;
import java.io.Serializable;
/**
 * Message sent when Requestor rejects a Debit Note or Invoice.
 */
@Schema(description = "Message sent when Requestor rejects a Debit Note or Invoice.")

public class Rejection implements Serializable{
  private static final long serialVersionUID = 1L;
  @JsonProperty("rejectionReason")
  private RejectionReason rejectionReason = null;

  @JsonProperty("totalAmountAccepted")
  private String totalAmountAccepted = null;

  @JsonProperty("message")
  private String message = null;

  public Rejection rejectionReason(RejectionReason rejectionReason) {
    this.rejectionReason = rejectionReason;
    return this;
  }

   /**
   * Get rejectionReason
   * @return rejectionReason
  **/
  @Schema(required = true, description = "")
  public RejectionReason getRejectionReason() {
    return rejectionReason;
  }

  public void setRejectionReason(RejectionReason rejectionReason) {
    this.rejectionReason = rejectionReason;
  }

  public Rejection totalAmountAccepted(String totalAmountAccepted) {
    this.totalAmountAccepted = totalAmountAccepted;
    return this;
  }

   /**
   * Get totalAmountAccepted
   * @return totalAmountAccepted
  **/
  @Schema(required = true, description = "")
  public String getTotalAmountAccepted() {
    return totalAmountAccepted;
  }

  public void setTotalAmountAccepted(String totalAmountAccepted) {
    this.totalAmountAccepted = totalAmountAccepted;
  }

  public Rejection message(String message) {
    this.message = message;
    return this;
  }

   /**
   * Get message
   * @return message
  **/
  @Schema(description = "")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Rejection rejection = (Rejection) o;
    return Objects.equals(this.rejectionReason, rejection.rejectionReason) &&
        Objects.equals(this.totalAmountAccepted, rejection.totalAmountAccepted) &&
        Objects.equals(this.message, rejection.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rejectionReason, totalAmountAccepted, message);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Rejection {\n");
    
    sb.append("    rejectionReason: ").append(toIndentedString(rejectionReason)).append("\n");
    sb.append("    totalAmountAccepted: ").append(toIndentedString(totalAmountAccepted)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
