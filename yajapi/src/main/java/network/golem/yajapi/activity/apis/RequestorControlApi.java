package network.golem.yajapi.activity.apis;

import network.golem.yajapi.activity.ApiException;
import network.golem.yajapi.activity.ApiClient;
import network.golem.yajapi.activity.Configuration;
import network.golem.yajapi.activity.Pair;

import javax.ws.rs.core.GenericType;

import java.math.BigDecimal;
import network.golem.yajapi.activity.models.ErrorMessage;
import network.golem.yajapi.activity.models.ExeScriptCommandResult;
import network.golem.yajapi.activity.models.ExeScriptRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestorControlApi {
  private ApiClient apiClient;

  public RequestorControlApi() {
    this(Configuration.getDefaultApiClient());
  }

  public RequestorControlApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * Creates new Activity based on given Agreement.
   * **Note:** This call shall get routed as a provider event (see ProviderEvent structure).
   * @param body  (required)
   * @return String
   * @throws ApiException if fails to make API call
   */
  public String createActivity(String body) throws ApiException {
    Object localVarPostBody = body;
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createActivity");
    }
    // create path and map variables
    String localVarPath = "/activity";

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
   * Destroys given Activity.
   * **Note:** This call shall get routed as a provider event (see ProviderEvent structure).
   * @param activityId  (required)
   * @throws ApiException if fails to make API call
   */
  public void destroyActivity(String activityId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'activityId' is set
    if (activityId == null) {
      throw new ApiException(400, "Missing the required parameter 'activityId' when calling destroyActivity");
    }
    // create path and map variables
    String localVarPath = "/activity/{activityId}"
      .replaceAll("\\{" + "activityId" + "\\}", apiClient.escapeString(activityId.toString()));

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
   * Executes an ExeScript batch within a given Activity.
   * **Note:** This call shall get routed directly to ExeUnit.
   * @param body  (required)
   * @param activityId  (required)
   * @return String
   * @throws ApiException if fails to make API call
   */
  public String exec(ExeScriptRequest body, String activityId) throws ApiException {
    Object localVarPostBody = body;
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling exec");
    }
    // verify the required parameter 'activityId' is set
    if (activityId == null) {
      throw new ApiException(400, "Missing the required parameter 'activityId' when calling exec");
    }
    // create path and map variables
    String localVarPath = "/activity/{activityId}/exec"
      .replaceAll("\\{" + "activityId" + "\\}", apiClient.escapeString(activityId.toString()));

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
   * Queries for ExeScript batch results.
   * **Note:** This call shall collect ExeScriptCommand result objects received directly from ExeUnit.
   * @param activityId  (required)
   * @param batchId  (required)
   * @param commandIndex Wait until command with the specified index finishes. Must be accompanied by a valid \&quot;timeout\&quot; query parameter.  (optional)
   * @param timeout How many seconds server should wait for new events (0.0 means it should return immediately if there are no events)  (optional)
   * @return List&lt;ExeScriptCommandResult&gt;
   * @throws ApiException if fails to make API call
   */
  public List<ExeScriptCommandResult> getExecBatchResults(String activityId, String batchId, BigDecimal commandIndex, Float timeout) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'activityId' is set
    if (activityId == null) {
      throw new ApiException(400, "Missing the required parameter 'activityId' when calling getExecBatchResults");
    }
    // verify the required parameter 'batchId' is set
    if (batchId == null) {
      throw new ApiException(400, "Missing the required parameter 'batchId' when calling getExecBatchResults");
    }
    // create path and map variables
    String localVarPath = "/activity/{activityId}/exec/{batchId}"
      .replaceAll("\\{" + "activityId" + "\\}", apiClient.escapeString(activityId.toString()))
      .replaceAll("\\{" + "batchId" + "\\}", apiClient.escapeString(batchId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "commandIndex", commandIndex));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "timeout", timeout));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "app_key" };

    GenericType<List<ExeScriptCommandResult>> localVarReturnType = new GenericType<List<ExeScriptCommandResult>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
}
