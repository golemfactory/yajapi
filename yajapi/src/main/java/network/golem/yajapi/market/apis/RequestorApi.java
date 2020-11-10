package network.golem.yajapi.market.apis;

import network.golem.yajapi.market.ApiException;
import network.golem.yajapi.market.ApiClient;
import network.golem.yajapi.market.Configuration;
import network.golem.yajapi.market.Pair;

import javax.ws.rs.core.GenericType;

import network.golem.yajapi.market.models.Agreement;
import network.golem.yajapi.market.models.AgreementProposal;
import network.golem.yajapi.market.models.Demand;
import network.golem.yajapi.market.models.ErrorMessage;
import network.golem.yajapi.market.models.PropertyQueryReply;
import network.golem.yajapi.market.models.Proposal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestorApi {
  private ApiClient apiClient;

  public RequestorApi() {
    this(Configuration.getDefaultApiClient());
  }

  public RequestorApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * Cancels agreement.
   * Causes:   - the awaiting &#x60;approveAgreement&#x60; on Provider side to return with &#x60;Cancelled&#x60; response.   - the awaiting &#x60;waitForApproval&#x60; local call to return with &#x60;Cancelled&#x60; response. 
   * @param agreementId  (required)
   * @throws ApiException if fails to make API call
   */
  public void cancelAgreement(String agreementId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'agreementId' is set
    if (agreementId == null) {
      throw new ApiException(400, "Missing the required parameter 'agreementId' when calling cancelAgreement");
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

    apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
  /**
   * Reads Market responses to published Demand.
   * This is a blocking operation. It will not return until there is at least one new event.  **Note**: When &#x60;collectOffers&#x60; is waiting, simultaneous call to &#x60;unsubscribeDemand&#x60; on the same &#x60;subscriptionId&#x60; should result in \&quot;Subscription does not exist\&quot; error returned from &#x60;collectOffers&#x60;.  **Note**: Specification requires this endpoint to support list of specific Proposal Ids to listen for messages related only to specific Proposals. This is not covered yet. 
   * @param subscriptionId  (required)
   * @param timeout How many seconds server should wait for new events (0.0 means it should return immediately if there are no events)  (optional)
   * @param maxEvents Maximum number of events that server should return at once (empty value means no limit).  (optional)
   * @return List&lt;Object&gt;
   * @throws ApiException if fails to make API call
   */
  public List<Object> collectOffers(String subscriptionId, Float timeout, Integer maxEvents) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'subscriptionId' is set
    if (subscriptionId == null) {
      throw new ApiException(400, "Missing the required parameter 'subscriptionId' when calling collectOffers");
    }
    // create path and map variables
    String localVarPath = "/demands/{subscriptionId}/events"
      .replaceAll("\\{" + "subscriptionId" + "\\}", apiClient.escapeString(subscriptionId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "timeout", timeout));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "maxEvents", maxEvents));



    final String[] localVarAccepts = {
      "application/json4", "application/json"
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
   * Sends Agreement proposal to the Provider.
   * Signs self-created Agreement and sends it to the Provider. This call should immediately follow &#x60;createAgreement&#x60;. 
   * @param agreementId  (required)
   * @throws ApiException if fails to make API call
   */
  public void confirmAgreement(String agreementId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'agreementId' is set
    if (agreementId == null) {
      throw new ApiException(400, "Missing the required parameter 'agreementId' when calling confirmAgreement");
    }
    // create path and map variables
    String localVarPath = "/agreements/{agreementId}/confirm"
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
   * Responds with a bespoke Demand to received Offer.
   * Creates and sends a modified version of original Demand (a counter-proposal) adjusted to previously received Proposal (ie. Offer). Changes Proposal state to &#x60;Draft&#x60;. Returns created Proposal id. 
   * @param body  (required)
   * @param subscriptionId  (required)
   * @param proposalId  (required)
   * @return String
   * @throws ApiException if fails to make API call
   */
  public String counterProposalDemand(Proposal body, String subscriptionId, String proposalId) throws ApiException {
    Object localVarPostBody = body;
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling counterProposalDemand");
    }
    // verify the required parameter 'subscriptionId' is set
    if (subscriptionId == null) {
      throw new ApiException(400, "Missing the required parameter 'subscriptionId' when calling counterProposalDemand");
    }
    // verify the required parameter 'proposalId' is set
    if (proposalId == null) {
      throw new ApiException(400, "Missing the required parameter 'proposalId' when calling counterProposalDemand");
    }
    // create path and map variables
    String localVarPath = "/demands/{subscriptionId}/proposals/{proposalId}"
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
   * Creates Agreement from selected Proposal.
   * Initiates the Agreement handshake phase.  Formulates an Agreement artifact from the Proposal indicated by the received Proposal Id. Created Agreement is in &#x60;Proposal&#x60; state.  The Approval Expiry Date is added to Agreement artifact and implies the effective timeout on the whole Agreement Confirmation sequence.  A successful call to &#x60;createAgreement&#x60; shall immediately be followed by a &#x60;confirmAgreement&#x60; and &#x60;waitForApproval&#x60; call in order to listen for responses from the Provider.  **Note**: Moves given Proposal to &#x60;Approved&#x60; state. 
   * @param body  (required)
   * @return String
   * @throws ApiException if fails to make API call
   */
  public String createAgreement(AgreementProposal body) throws ApiException {
    Object localVarPostBody = body;
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createAgreement");
    }
    // create path and map variables
    String localVarPath = "/agreements";

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
   * Fetches all active Demands which have been published by the Requestor.
   * 
   * @return List&lt;Object&gt;
   * @throws ApiException if fails to make API call
   */
  public List<Object> getDemands() throws ApiException {
    Object localVarPostBody = null;
    // create path and map variables
    String localVarPath = "/demands";

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
   * Fetches Proposal (Offer) with given id.
   * 
   * @param subscriptionId  (required)
   * @param proposalId  (required)
   * @return Proposal
   * @throws ApiException if fails to make API call
   */
  public Proposal getProposalOffer(String subscriptionId, String proposalId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'subscriptionId' is set
    if (subscriptionId == null) {
      throw new ApiException(400, "Missing the required parameter 'subscriptionId' when calling getProposalOffer");
    }
    // verify the required parameter 'proposalId' is set
    if (proposalId == null) {
      throw new ApiException(400, "Missing the required parameter 'proposalId' when calling getProposalOffer");
    }
    // create path and map variables
    String localVarPath = "/demands/{subscriptionId}/proposals/{proposalId}"
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
  public void queryReplyDemands(PropertyQueryReply body, String subscriptionId, String queryId) throws ApiException {
    Object localVarPostBody = body;
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling queryReplyDemands");
    }
    // verify the required parameter 'subscriptionId' is set
    if (subscriptionId == null) {
      throw new ApiException(400, "Missing the required parameter 'subscriptionId' when calling queryReplyDemands");
    }
    // verify the required parameter 'queryId' is set
    if (queryId == null) {
      throw new ApiException(400, "Missing the required parameter 'queryId' when calling queryReplyDemands");
    }
    // create path and map variables
    String localVarPath = "/demands/{subscriptionId}/propertyQuery/{queryId}"
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
   * Rejects Proposal (Offer).
   * Effectively ends a Negotiation chain - it explicitly indicates that the sender will not create another counter-Proposal. 
   * @param subscriptionId  (required)
   * @param proposalId  (required)
   * @throws ApiException if fails to make API call
   */
  public void rejectProposalOffer(String subscriptionId, String proposalId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'subscriptionId' is set
    if (subscriptionId == null) {
      throw new ApiException(400, "Missing the required parameter 'subscriptionId' when calling rejectProposalOffer");
    }
    // verify the required parameter 'proposalId' is set
    if (proposalId == null) {
      throw new ApiException(400, "Missing the required parameter 'proposalId' when calling rejectProposalOffer");
    }
    // create path and map variables
    String localVarPath = "/demands/{subscriptionId}/proposals/{proposalId}"
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
   * Publishes Requestor capabilities via Demand.
   * Demand object can be considered an \&quot;open\&quot; or public Demand, as it is not directed at a specific Provider, but rather is sent to the market so that the matching mechanism implementation can associate relevant Offers.  **Note**: it is an \&quot;atomic\&quot; operation, ie. as soon as Subscription is placed, the Demand is published on the market. 
   * @param body  (required)
   * @return String
   * @throws ApiException if fails to make API call
   */
  public String subscribeDemand(Demand body) throws ApiException {
    Object localVarPostBody = body;
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling subscribeDemand");
    }
    // create path and map variables
    String localVarPath = "/demands";

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
   * Stop subscription for previously published Demand.
   * Stop receiving Proposals.  **Note**: this will terminate all pending &#x60;collectOffers&#x60; calls on this subscription. This implies, that client code should not &#x60;unsubscribeDemand&#x60; before it has received all expected/useful inputs from &#x60;collectOffers&#x60;. 
   * @param subscriptionId  (required)
   * @throws ApiException if fails to make API call
   */
  public void unsubscribeDemand(String subscriptionId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'subscriptionId' is set
    if (subscriptionId == null) {
      throw new ApiException(400, "Missing the required parameter 'subscriptionId' when calling unsubscribeDemand");
    }
    // create path and map variables
    String localVarPath = "/demands/{subscriptionId}"
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
  /**
   * Waits for Agreement approval by the Provider.
   * This is a blocking operation. The call may be aborted by Requestor caller code. After the call is aborted, another &#x60;waitForApproval&#x60; call can be raised on the same Agreement Id. It returns one of the following options: * &#x60;Approved&#x60; - Indicates that the Agreement has been approved by the Provider.   - The Provider is now ready to accept a request to start an Activity     as described in the negotiated agreement.   - The Providers’s corresponding &#x60;approveAgreement&#x60; call returns &#x60;Approved&#x60; after     this on the Provider side.  * &#x60;Rejected&#x60; - Indicates that the Provider has called &#x60;rejectAgreement&#x60;,   which effectively stops the Agreement handshake. The Requestor may attempt   to return to the Negotiation phase by sending a new Proposal.  * &#x60;Cancelled&#x60; - Indicates that the Requestor himself has called  &#x60;cancelAgreement&#x60;, which effectively stops the Agreement handshake. 
   * @param agreementId  (required)
   * @param timeout How many seconds server should wait for new events (0.0 means it should return immediately if there are no events)  (optional)
   * @return String
   * @throws ApiException if fails to make API call
   */
  public String waitForApproval(String agreementId, Float timeout) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'agreementId' is set
    if (agreementId == null) {
      throw new ApiException(400, "Missing the required parameter 'agreementId' when calling waitForApproval");
    }
    // create path and map variables
    String localVarPath = "/agreements/{agreementId}/wait"
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
}
