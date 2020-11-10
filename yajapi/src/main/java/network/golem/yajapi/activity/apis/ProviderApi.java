package network.golem.yajapi.activity.apis;

import network.golem.yajapi.activity.ApiException;
import network.golem.yajapi.activity.ApiClient;
import network.golem.yajapi.activity.Configuration;
import network.golem.yajapi.activity.Pair;

import javax.ws.rs.core.GenericType;

import network.golem.yajapi.activity.models.ActivityState;
import network.golem.yajapi.activity.models.ErrorMessage;

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
   * Fetch Requestor command events.
   * 
   * @param timeout How many seconds server should wait for new events (0.0 means it should return immediately if there are no events)  (optional)
   * @param maxEvents Maximum number of events that server should return at once (empty value means no limit).  (optional)
   * @return List&lt;Object&gt;
   * @throws ApiException if fails to make API call
   */
  public List<Object> collectActivityEvents(Float timeout, Integer maxEvents) throws ApiException {
    Object localVarPostBody = null;
    // create path and map variables
    String localVarPath = "/events";

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
   * Lists all Activity ids for Provider identified by app_key.
   * 
   * @return List&lt;String&gt;
   * @throws ApiException if fails to make API call
   */
  public List<String> listActivities() throws ApiException {
    Object localVarPostBody = null;
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
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "app_key" };

    GenericType<List<String>> localVarReturnType = new GenericType<List<String>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Set state of specified Activity.
   * 
   * @param body  (required)
   * @param activityId  (required)
   * @throws ApiException if fails to make API call
   */
  public void setActivityState(ActivityState body, String activityId) throws ApiException {
    Object localVarPostBody = body;
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling setActivityState");
    }
    // verify the required parameter 'activityId' is set
    if (activityId == null) {
      throw new ApiException(400, "Missing the required parameter 'activityId' when calling setActivityState");
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
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "app_key" };

    apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
}
