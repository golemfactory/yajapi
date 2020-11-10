/*
 * Yagna Market API
 *  ## Yagna Market The Yagna Market is a core component of the Yagna Network, which enables computational Offers and Demands circulation. The Market is open for all entities willing to buy computations (Demands) or monetize computational resources (Offers). ## Yagna Market API The Yagna Market API is the entry to the Yagna Market through which Requestors and Providers can publish their Demands and Offers respectively, find matching counterparty, conduct negotiations and make an agreement.  This version of Market API conforms with capability level 1 of the <a href=\"https://docs.google.com/document/d/1Zny_vfgWV-hcsKS7P-Kdr3Fb0dwfl-6T_cYKVQ9mkNg\"> Market API specification</a>.  Market API contains two roles: Requestors and Providers which are symmetrical most of the time (excluding agreement phase). 
 *
 * OpenAPI spec version: 1.6.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package network.golem.yajapi.market.models;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.io.Serializable;
/**
 * AgreementProposal
 */


public class AgreementProposal implements Serializable{
  private static final long serialVersionUID = 1L;
  @JsonProperty("proposalId")
  private String proposalId = null;

  @JsonProperty("validTo")
  private OffsetDateTime validTo = null;

  public AgreementProposal proposalId(String proposalId) {
    this.proposalId = proposalId;
    return this;
  }

   /**
   * id of the proposal to be promoted to the Agreement
   * @return proposalId
  **/
  @Schema(required = true, description = "id of the proposal to be promoted to the Agreement")
  public String getProposalId() {
    return proposalId;
  }

  public void setProposalId(String proposalId) {
    this.proposalId = proposalId;
  }

  public AgreementProposal validTo(OffsetDateTime validTo) {
    this.validTo = validTo;
    return this;
  }

   /**
   * End of validity period. Agreement needs to be accepted, rejected or cancellled before this date; otherwise will expire 
   * @return validTo
  **/
  @Schema(required = true, description = "End of validity period. Agreement needs to be accepted, rejected or cancellled before this date; otherwise will expire ")
  public OffsetDateTime getValidTo() {
    return validTo;
  }

  public void setValidTo(OffsetDateTime validTo) {
    this.validTo = validTo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AgreementProposal agreementProposal = (AgreementProposal) o;
    return Objects.equals(this.proposalId, agreementProposal.proposalId) &&
        Objects.equals(this.validTo, agreementProposal.validTo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(proposalId, validTo);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AgreementProposal {\n");
    
    sb.append("    proposalId: ").append(toIndentedString(proposalId)).append("\n");
    sb.append("    validTo: ").append(toIndentedString(validTo)).append("\n");
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
