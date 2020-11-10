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
import network.golem.yajapi.market.models.Event;
import network.golem.yajapi.market.models.Proposal;
import java.io.Serializable;
/**
 * ProposalEvent
 */


public class ProposalEvent extends Event implements Serializable{
  private static final long serialVersionUID = 1L;
  @JsonProperty("proposal")
  private Proposal proposal = null;

  public ProposalEvent proposal(Proposal proposal) {
    this.proposal = proposal;
    return this;
  }

   /**
   * Get proposal
   * @return proposal
  **/
  @Schema(description = "")
  public Proposal getProposal() {
    return proposal;
  }

  public void setProposal(Proposal proposal) {
    this.proposal = proposal;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProposalEvent proposalEvent = (ProposalEvent) o;
    return Objects.equals(this.proposal, proposalEvent.proposal) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(proposal, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProposalEvent {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    proposal: ").append(toIndentedString(proposal)).append("\n");
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
