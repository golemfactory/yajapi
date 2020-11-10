package network.golem.yajapi.activity.apis;

import network.golem.yajapi.activity.ApiException;
import network.golem.yajapi.activity.ApiClient;
import network.golem.yajapi.activity.Configuration;
import network.golem.yajapi.activity.Pair;

import javax.ws.rs.core.GenericType;

import network.golem.yajapi.activity.models.ActivityState;
import network.golem.yajapi.activity.models.ErrorMessage;
import network.golem.yajapi.activity.models.ExeScriptCommandState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestorStateApi {
  private ApiClient apiClient;

  public RequestorStateApi() {
    this(Configuration.getDefaultApiClient());
  }

  public RequestorStateApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * Get state of specified Activity.
   * 
   * @param activityId  (required)
   * @return ActivityState
   * @throws ApiException if fails to make API call
   */
  public ActivityState getActivityState(String activityId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'activityId' is set
    if (activityId == null) {
      throw new ApiException(400, "Missing the required parameter 'activityId' when calling getActivityState");
    }
    // create path and map variables
    String localVarPath = "/activity/{activityId}/state"
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

    GenericType<ActivityState> localVarReturnType = new GenericType<ActivityState>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Get usage of specified Activity.
   * 
   * @param activityId  (required)
   * @return List&lt;Double&gt;
   * @throws ApiException if fails to make API call
   */
  public List<Double> getActivityUsage(String activityId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'activityId' is set
    if (activityId == null) {
      throw new ApiException(400, "Missing the required parameter 'activityId' when calling getActivityUsage");
    }
    // create path and map variables
    String localVarPath = "/activity/{activityId}/usage"
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

    GenericType<List<Double>> localVarReturnType = new GenericType<List<Double>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Get running command for a specified Activity.
   * **Note:** This call shall get routed directly to ExeUnit.
   * @param activityId  (required)
   * @return ExeScriptCommandState
   * @throws ApiException if fails to make API call
   */
  public ExeScriptCommandState getRunningCommand(String activityId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'activityId' is set
    if (activityId == null) {
      throw new ApiException(400, "Missing the required parameter 'activityId' when calling getRunningCommand");
    }
    // create path and map variables
    String localVarPath = "/activity/{activityId}/command"
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

    GenericType<ExeScriptCommandState> localVarReturnType = new GenericType<ExeScriptCommandState>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
}
