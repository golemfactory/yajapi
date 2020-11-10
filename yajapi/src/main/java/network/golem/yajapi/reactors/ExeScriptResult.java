package network.golem.yajapi.reactors;

import network.golem.yajapi.activity.ApiException;
import network.golem.yajapi.activity.models.ExeScriptCommandResult;

import java.util.ArrayList;
import java.util.List;

public class ExeScriptResult {
    private final String activityId;
    private final String batchId;
    private boolean success;
    private List<ExeScriptCommandResult> okResults = new ArrayList<>();
    private List<ExeScriptCommandResult> errorResults = new ArrayList<>();
    private network.golem.yajapi.activity.ApiException apiException;

    public ExeScriptResult(String activityId, String batchId) {
        this.activityId = activityId;
        this.batchId = batchId;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getBatchId() {
        return batchId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ExeScriptCommandResult> getOkResults() {
        return okResults;
    }

    public void setOkResults(List<ExeScriptCommandResult> okResults) {
        this.okResults = okResults;
    }

    public List<ExeScriptCommandResult> getErrorResults() {
        return errorResults;
    }

    public void setErrorResults(List<ExeScriptCommandResult> errorResults) {
        this.errorResults = errorResults;
    }

    public ApiException getApiException() {
        return apiException;
    }

    public void setApiException(ApiException apiException) {
        this.apiException = apiException;
    }
}
