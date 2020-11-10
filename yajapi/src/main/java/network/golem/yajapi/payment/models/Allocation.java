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
import java.time.OffsetDateTime;
import java.io.Serializable;
/**
 * ADD DESCRIPTION
 */
@Schema(description = "ADD DESCRIPTION")

public class Allocation implements Serializable{
  private static final long serialVersionUID = 1L;
  @JsonProperty("allocationId")
  private String allocationId = null;

  @JsonProperty("address")
  private String address = null;

  @JsonProperty("paymentPlatform")
  private String paymentPlatform = null;

  @JsonProperty("totalAmount")
  private String totalAmount = null;

  @JsonProperty("spentAmount")
  private String spentAmount = null;

  @JsonProperty("remainingAmount")
  private String remainingAmount = null;

  @JsonProperty("timeout")
  private OffsetDateTime timeout = null;

  @JsonProperty("makeDeposit")
  private Boolean makeDeposit = null;

   /**
   * Get allocationId
   * @return allocationId
  **/
  @Schema(required = true, description = "")
  public String getAllocationId() {
    return allocationId;
  }

  public Allocation address(String address) {
    this.address = address;
    return this;
  }

   /**
   * Get address
   * @return address
  **/
  @Schema(description = "")
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Allocation paymentPlatform(String paymentPlatform) {
    this.paymentPlatform = paymentPlatform;
    return this;
  }

   /**
   * Get paymentPlatform
   * @return paymentPlatform
  **/
  @Schema(description = "")
  public String getPaymentPlatform() {
    return paymentPlatform;
  }

  public void setPaymentPlatform(String paymentPlatform) {
    this.paymentPlatform = paymentPlatform;
  }

  public Allocation totalAmount(String totalAmount) {
    this.totalAmount = totalAmount;
    return this;
  }

   /**
   * Get totalAmount
   * @return totalAmount
  **/
  @Schema(required = true, description = "")
  public String getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(String totalAmount) {
    this.totalAmount = totalAmount;
  }

   /**
   * Get spentAmount
   * @return spentAmount
  **/
  @Schema(required = true, description = "")
  public String getSpentAmount() {
    return spentAmount;
  }

   /**
   * Get remainingAmount
   * @return remainingAmount
  **/
  @Schema(required = true, description = "")
  public String getRemainingAmount() {
    return remainingAmount;
  }

  public Allocation timeout(OffsetDateTime timeout) {
    this.timeout = timeout;
    return this;
  }

   /**
   * Get timeout
   * @return timeout
  **/
  @Schema(description = "")
  public OffsetDateTime getTimeout() {
    return timeout;
  }

  public void setTimeout(OffsetDateTime timeout) {
    this.timeout = timeout;
  }

  public Allocation makeDeposit(Boolean makeDeposit) {
    this.makeDeposit = makeDeposit;
    return this;
  }

   /**
   * Get makeDeposit
   * @return makeDeposit
  **/
  @Schema(required = true, description = "")
  public Boolean isMakeDeposit() {
    return makeDeposit;
  }

  public void setMakeDeposit(Boolean makeDeposit) {
    this.makeDeposit = makeDeposit;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Allocation allocation = (Allocation) o;
    return Objects.equals(this.allocationId, allocation.allocationId) &&
        Objects.equals(this.address, allocation.address) &&
        Objects.equals(this.paymentPlatform, allocation.paymentPlatform) &&
        Objects.equals(this.totalAmount, allocation.totalAmount) &&
        Objects.equals(this.spentAmount, allocation.spentAmount) &&
        Objects.equals(this.remainingAmount, allocation.remainingAmount) &&
        Objects.equals(this.timeout, allocation.timeout) &&
        Objects.equals(this.makeDeposit, allocation.makeDeposit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(allocationId, address, paymentPlatform, totalAmount, spentAmount, remainingAmount, timeout, makeDeposit);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Allocation {\n");
    
    sb.append("    allocationId: ").append(toIndentedString(allocationId)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    paymentPlatform: ").append(toIndentedString(paymentPlatform)).append("\n");
    sb.append("    totalAmount: ").append(toIndentedString(totalAmount)).append("\n");
    sb.append("    spentAmount: ").append(toIndentedString(spentAmount)).append("\n");
    sb.append("    remainingAmount: ").append(toIndentedString(remainingAmount)).append("\n");
    sb.append("    timeout: ").append(toIndentedString(timeout)).append("\n");
    sb.append("    makeDeposit: ").append(toIndentedString(makeDeposit)).append("\n");
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