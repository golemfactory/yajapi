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
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
/**
 * PropertyQuery
 */


public class PropertyQuery implements Serializable{
  private static final long serialVersionUID = 1L;
  @JsonProperty("issuerProperties")
  private Object issuerProperties = null;

  @JsonProperty("queryId")
  private String queryId = null;

  @JsonProperty("queriedProperties")
  private List<String> queriedProperties = null;

  public PropertyQuery issuerProperties(Object issuerProperties) {
    this.issuerProperties = issuerProperties;
    return this;
  }

   /**
   * Get issuerProperties
   * @return issuerProperties
  **/
  @Schema(description = "")
  public Object getIssuerProperties() {
    return issuerProperties;
  }

  public void setIssuerProperties(Object issuerProperties) {
    this.issuerProperties = issuerProperties;
  }

  public PropertyQuery queryId(String queryId) {
    this.queryId = queryId;
    return this;
  }

   /**
   * Get queryId
   * @return queryId
  **/
  @Schema(description = "")
  public String getQueryId() {
    return queryId;
  }

  public void setQueryId(String queryId) {
    this.queryId = queryId;
  }

  public PropertyQuery queriedProperties(List<String> queriedProperties) {
    this.queriedProperties = queriedProperties;
    return this;
  }

  public PropertyQuery addQueriedPropertiesItem(String queriedPropertiesItem) {
    if (this.queriedProperties == null) {
      this.queriedProperties = new ArrayList<>();
    }
    this.queriedProperties.add(queriedPropertiesItem);
    return this;
  }

   /**
   * Get queriedProperties
   * @return queriedProperties
  **/
  @Schema(description = "")
  public List<String> getQueriedProperties() {
    return queriedProperties;
  }

  public void setQueriedProperties(List<String> queriedProperties) {
    this.queriedProperties = queriedProperties;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PropertyQuery propertyQuery = (PropertyQuery) o;
    return Objects.equals(this.issuerProperties, propertyQuery.issuerProperties) &&
        Objects.equals(this.queryId, propertyQuery.queryId) &&
        Objects.equals(this.queriedProperties, propertyQuery.queriedProperties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(issuerProperties, queryId, queriedProperties);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PropertyQuery {\n");
    
    sb.append("    issuerProperties: ").append(toIndentedString(issuerProperties)).append("\n");
    sb.append("    queryId: ").append(toIndentedString(queryId)).append("\n");
    sb.append("    queriedProperties: ").append(toIndentedString(queriedProperties)).append("\n");
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