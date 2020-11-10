package network.golem.yajapi.market.apis;

import network.golem.yajapi.market.ApiException;
import network.golem.yajapi.market.ApiClient;
import network.golem.yajapi.market.Configuration;
import network.golem.yajapi.market.Pair;

import javax.ws.rs.core.GenericType;

import network.golem.yajapi.market.models.Agreement;
import network.golem.yajapi.market.models.ErrorMessage;
import network.golem.yajapi.market.models.Offer;
import network.golem.yajapi.market.models.PropertyQueryReply;
import network.golem.yajapi.market.models.Proposal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProviderApi {
  private ApiClient apiClient;

  public ProviderApi() {
    this(Configuration.getDefaultApiClient());
  }

  public ProviderApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * Approves Agreement proposed by the Reqestor.
   * This is a blocking operation. The call may be aborted by Provider caller code. After the call is aborted or timed out, another &#x60;approveAgreement&#x60; call can be raised on the same &#x60;agreementId&#x60;. It returns one of the following options: * &#x60;Approved&#x60; - Indicates that the approved Agreement has been successfully delivered to the Requestor and acknowledged.   - The Requestor side has been notified about the Provider’s commitment     to the Agreement.   - The Provider is now ready to accept a request to start an Activity     as described in the negotiated agreement.   - The Requestor’s corresponding &#x60;waitForApproval&#x60; call returns &#x60;Approved&#x60; after     the one on the Provider side.  * &#x60;Cancelled&#x60; - Indicates that before delivering the approved Agreement, the Requestor has called &#x60;cancelAgreement&#x60;, thus invalidating the Agreement. The Provider may attempt to return to the Negotiation phase by sending a new Proposal.  **Note**: It is expected from the Provider node implementation to “ring-fence” the resources required to fulfill the Agreement before the ApproveAgreement is sent. However, the resources should not be fully committed until &#x60;Approved&#x60; response is received from the &#x60;approveAgreement&#x60; call.  **Note**: Mutually exclusive with &#x60;rejectAgreement&#x60;. 
   * @param agreementId  (required)
   * @param timeout How many seconds server should wait for new events (0.0 means it should return immediately if there are no events)  (optional)
   * @return String
   * @throws ApiException if fails to make API call
   */
  public String approveAgreement(String agreementId, Float timeout) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'agreementId' is set
    if (agreementId == null) {
      throw new ApiException(400, "Missing the required parameter 'agreementId' when calling approveAgreement");
    }
    // create path and map variables
    String localVarPath = "/agreements/{agreementId}/approve"
      .replaceAll("\\{" + "agreementId" + "\\}", apiClient.escapeString(agreementId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "timeout", timeout));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "app_key" };

    GenericType<String> localVarReturnType = new GenericType<String>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Reads Market responses to published Offer.
   * This is a blocking operation. It will not return until there is at least one new event.  **Note**: When &#x60;collectDemands&#x60; is waiting, simultaneous call to &#x60;unsubscribeOffer&#x60; on the same &#x60;subscriptionId&#x60; should result in \&quot;Subscription does not exist\&quot; error returned from &#x60;collectDemands&#x60;.  **Note**: Specification requires this endpoint to support list of specific Proposal Ids to listen for messages related only to specific Proposals. This is not covered yet. 
   * @param subscriptionId  (required)
   * @param timeout How many seconds server should wait for new events (0.0 means it should return immediately if there are no events)  (optional)
   * @param maxEvents Maximum number of events that server should return at once (empty value means no limit).  (optional)
   * @return List&lt;Object&gt;
   * @throws ApiException if fails to make API call
   */
  public List<Object> collectDemands(String subscriptionId, Float timeout, Integer maxEvents) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'subscriptionId' is set
    if (subscriptionId == null) {
      throw new ApiException(400, "Missing the required parameter 'subscriptionId' when calling collectDemands");
    }
    // create path and map variables
    String localVarPath = "/offers/{subscriptionId}/events"
      .replaceAll("\\{" + "subscriptionId" + "\\}", apiClient.escapeString(subscriptionId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "timeout", timeout));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "maxEvents", maxEvents));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "app_key" };

    GenericType<List<Object>> localVarReturnType = new GenericType<List<Object>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Responds with a bespoke Offer to received Demand.
   * Creates and sends a modified version of original Offer (a counter-proposal) adjusted to previously received Proposal (ie. Demand). Changes Proposal state to &#x60;Draft&#x60;. Returns created Proposal id. 
   * @param body  (required)
   * @param subscriptionId  (required)
   * @param proposalId  (required)
   * @return String
   * @throws ApiException if fails to make API call
   */
  public String counterProposalOffer(Proposal body, String subscriptionId, String proposalId) throws ApiException {
    Object localVarPostBody = body;
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling counterProposalOffer");
    }
    // verify the required parameter 'subscriptionId' is set
    if (subscriptionId == null) {
      throw new ApiException(400, "Missing the required parameter 'subscriptionId' when calling counterProposalOffer");
    }
    // verify the required parameter 'proposalId' is set
    if (proposalId == null) {
      throw new ApiException(400, "Missing the required parameter 'proposalId' when calling counterProposalOffer");
    }
    // create path and map variables
    String localVarPath = "/offers/{subscriptionId}/proposals/{proposalId}"
      .replaceAll("\\{" + "subscriptionId" + "\\}", apiClient.escapeString(subscriptionId.toString()))
      .replaceAll("\\{" + "proposalId" + "\\}", apiClient.escapeString(proposalId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "app_key" };

    GenericType<String> localVarReturnType = new GenericType<String>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Fetches agreement with given agreement id.
   * 
   * @param agreementId  (required)
   * @return Agreement
   * @throws ApiException if fails to make API call
   */
  public Agreement getAgreement(String agreementId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'agreementId' is set
    if (agreementId == null) {
      throw new ApiException(400, "Missing the required parameter 'agreementId' when calling getAgreement");
    }
    // create path and map variables
    String localVarPath = "/agreements/{agreementId}"
      .replaceAll("\\{" + "agreementId" + "\\}", apiClient.escapeString(agreementId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "app_key" };

    GenericType<Agreement> localVarReturnType = new GenericType<Agreement>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Fetches all active Offers which have been published by the Provider.
   * 
   * @return List&lt;Object&gt;
   * @throws ApiException if fails to make API call
   */
  public List<Object> getOffers() throws ApiException {
    Object localVarPostBody = null;
    // create path and map variables
    String localVarPath = "/offers";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "app_key" };

    GenericType<List<Object>> localVarReturnType = new GenericType<List<Object>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Fetches Proposal (Demand) with given id.
   * 
   * @param subscriptionId  (required)
   * @param proposalId  (required)
   * @return Proposal
   * @throws ApiException if fails to make API call
   */
  public Proposal getProposalDemand(String subscriptionId, String proposalId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'subscriptionId' is set
    if (subscriptionId == null) {
      throw new ApiException(400, "Missing the required parameter 'subscriptionId' when calling getProposalDemand");
    }
    // verify the required parameter 'proposalId' is set
    if (proposalId == null) {
      throw new ApiException(400, "Missing the required parameter 'proposalId' when calling getProposalDemand");
    }
    // create path and map variables
    String localVarPath = "/offers/{subscriptionId}/proposals/{proposalId}"
      .replaceAll("\\{" + "subscriptionId" + "\\}", apiClient.escapeString(subscriptionId.toString()))
      .replaceAll("\\{" + "proposalId" + "\\}", apiClient.escapeString(proposalId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "app_key" };

    GenericType<Proposal> localVarReturnType = new GenericType<Proposal>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Handles dynamic property query.
   * The Market Matching Mechanism, when resolving the match relation for the specific Demand-Offer pair, is to detect the “dynamic” properties required (via constraints) by the other side. At this point, it is able to query the issuing node for those properties and submit the other side’s requested properties as the context of the query.  **Note**: The property query responses may be submitted in “chunks”, ie. the responder may choose to resolve ‘quick’/lightweight’ properties faster and provide response sooner, while still working on more time-consuming properties in the background. Therefore the response contains both the resolved properties, as well as list of properties which responder knows still require resolution.  **Note**: This method must be implemented for Market API Capability Level 2. 
   * @param body  (required)
   * @param subscriptionId  (required)
   * @param queryId  (required)
   * @throws ApiException if fails to make API call
   */
  public void queryReplyOffers(PropertyQueryReply body, String subscriptionId, String queryId) throws ApiException {
    Object localVarPostBody = body;
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling queryReplyOffers");
    }
    // verify the required parameter 'subscriptionId' is set
    if (subscriptionId == null) {
      throw new ApiException(400, "Missing the required parameter 'subscriptionId' when calling queryReplyOffers");
    }
    // verify the required parameter 'queryId' is set
    if (queryId == null) {
      throw new ApiException(400, "Missing the required parameter 'queryId' when calling queryReplyOffers");
    }
    // create path and map variables
    String localVarPath = "/offers/{subscriptionId}/propertyQuery/{queryId}"
      .replaceAll("\\{" + "subscriptionId" + "\\}", apiClient.escapeString(subscriptionId.toString()))
      .replaceAll("\\{" + "queryId" + "\\}", apiClient.escapeString(queryId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "app_key" };

    apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
  /**
   * Rejects Agreement proposed by the Requestor.
   * The Requestor side is notified about the Provider’s decision to reject a negotiated agreement. This effectively stops the Agreement handshake.  **Note**: Mutually exclusive with &#x60;approveAgreement&#x60;. 
   * @param agreementId  (required)
   * @throws ApiException if fails to make API call
   */
  public void rejectAgreement(String agreementId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'agreementId' is set
    if (agreementId == null) {
      throw new ApiException(400, "Missing the required parameter 'agreementId' when calling rejectAgreement");
    }
    // create path and map variables
    String localVarPath = "/agreements/{agreementId}/reject"
      .replaceAll("\\{" + "agreementId" + "\\}", apiClient.escapeString(agreementId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "app_key" };

    apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
  /**
   * Rejects Proposal (Demand).
   * Effectively ends a Negotiation chain - it explicitly indicates that the sender will not create another counter-Proposal. 
   * @param subscriptionId  (required)
   * @param proposalId  (required)
   * @throws ApiException if fails to make API call
   */
  public void rejectProposalDemand(String subscriptionId, String proposalId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'subscriptionId' is set
    if (subscriptionId == null) {
      throw new ApiException(400, "Missing the required parameter 'subscriptionId' when calling rejectProposalDemand");
    }
    // verify the required parameter 'proposalId' is set
    if (proposalId == null) {
      throw new ApiException(400, "Missing the required parameter 'proposalId' when calling rejectProposalDemand");
    }
    // create path and map variables
    String localVarPath = "/offers/{subscriptionId}/proposals/{proposalId}"
      .replaceAll("\\{" + "subscriptionId" + "\\}", apiClient.escapeString(subscriptionId.toString()))
      .replaceAll("\\{" + "proposalId" + "\\}", apiClient.escapeString(proposalId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "app_key" };

    apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
  /**
   * Publishes Provider capabilities via Offer.
   * Offer object can be considered an \&quot;open\&quot; or public Offer, as it is not directed at a specific Requestor, but rather is sent to the market so that the matching mechanism implementation can associate relevant Demands.  **Note**: it is an \&quot;atomic\&quot; operation, ie. as soon as Subscription is placed, the Offer is published on the market. 
   * @param body  (required)
   * @return String
   * @throws ApiException if fails to make API call
   */
  public String subscribeOffer(Offer body) throws ApiException {
    Object localVarPostBody = body;
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling subscribeOffer");
    }
    // create path and map variables
    String localVarPath = "/offers";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "app_key" };

    GenericType<String> localVarReturnType = new GenericType<String>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Terminates approved Agreement.
   * Method to finish/close the Agreement while in &#x60;Approved&#x60; state. The other party gets notified about calling party decision to terminate a \&quot;running\&quot; agreement. **Note**: Can be invoked at any time after Agreement was approved by both sides. Financial and reputational consequences are not defined by this specification. 
   * @param agreementId  (required)
   * @throws ApiException if fails to make API call
   */
  public void terminateAgreement(String agreementId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'agreementId' is set
    if (agreementId == null) {
      throw new ApiException(400, "Missing the required parameter 'agreementId' when calling terminateAgreement");
    }
    // create path and map variables
    String localVarPath = "/agreements/{agreementId}/terminate"
      .replaceAll("\\{" + "agreementId" + "\\}", apiClient.escapeString(agreementId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "app_key" };

    apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
  /**
   * Stop subscription for previously published Offer.
   * Stop receiving Proposals.  **Note**: this will terminate all pending &#x60;collectDemands&#x60; calls on this subscription. This implies, that client code should not &#x60;unsubscribeOffer&#x60; before it has received all expected/useful inputs from &#x60;collectDemands&#x60;. 
   * @param subscriptionId  (required)
   * @throws ApiException if fails to make API call
   */
  public void unsubscribeOffer(String subscriptionId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'subscriptionId' is set
    if (subscriptionId == null) {
      throw new ApiException(400, "Missing the required parameter 'subscriptionId' when calling unsubscribeOffer");
    }
    // create path and map variables
    String localVarPath = "/offers/{subscriptionId}"
      .replaceAll("\\{" + "subscriptionId" + "\\}", apiClient.escapeString(subscriptionId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "app_key" };

    apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
}
