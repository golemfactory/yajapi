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
import network.golem.yajapi.payment.models.EventType;
import java.io.Serializable;
/**
 * DebitNoteEvent
 */


public class DebitNoteEvent implements Serializable{
  private static final long serialVersionUID = 1L;
  @JsonProperty("debitNoteId")
  private String debitNoteId = null;

  @JsonProperty("timestamp")
  private OffsetDateTime timestamp = null;

  @JsonProperty("details")
  private Object details = null;

  @JsonProperty("eventType")
  private EventType eventType = null;

  public DebitNoteEvent debitNoteId(String debitNoteId) {
    this.debitNoteId = debitNoteId;
    return this;
  }

   /**
   * Get debitNoteId
   * @return debitNoteId
  **/
  @Schema(required = true, description = "")
  public String getDebitNoteId() {
    return debitNoteId;
  }

  public void setDebitNoteId(String debitNoteId) {
    this.debitNoteId = debitNoteId;
  }

  public DebitNoteEvent timestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

   /**
   * Get timestamp
   * @return timestamp
  **/
  @Schema(required = true, description = "")
  public OffsetDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public DebitNoteEvent details(Object details) {
    this.details = details;
    return this;
  }

   /**
   * Get details
   * @return details
  **/
  @Schema(description = "")
  public Object getDetails() {
    return details;
  }

  public void setDetails(Object details) {
    this.details = details;
  }

  public DebitNoteEvent eventType(EventType eventType) {
    this.eventType = eventType;
    return this;
  }

   /**
   * Get eventType
   * @return eventType
  **/
  @Schema(required = true, description = "")
  public EventType getEventType() {
    return eventType;
  }

  public void setEventType(EventType eventType) {
    this.eventType = eventType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DebitNoteEvent debitNoteEvent = (DebitNoteEvent) o;
    return Objects.equals(this.debitNoteId, debitNoteEvent.debitNoteId) &&
        Objects.equals(this.timestamp, debitNoteEvent.timestamp) &&
        Objects.equals(this.details, debitNoteEvent.details) &&
        Objects.equals(this.eventType, debitNoteEvent.eventType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(debitNoteId, timestamp, details, eventType);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DebitNoteEvent {\n");
    
    sb.append("    debitNoteId: ").append(toIndentedString(debitNoteId)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    details: ").append(toIndentedString(details)).append("\n");
    sb.append("    eventType: ").append(toIndentedString(eventType)).append("\n");
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
