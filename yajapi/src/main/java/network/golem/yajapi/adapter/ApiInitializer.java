package network.golem.yajapi.adapter;

public class ApiInitializer {
    private static boolean done = false;

    public synchronized static void initialize() {
        if (done) return;

        String address = "http://127.0.0.1:7465";
        String appKey = "93f4284557824f5ba6ebeaff1e10a089";

        network.golem.yajapi.payment.ApiClient paymentApiClient = network.golem.yajapi.payment.Configuration.getDefaultApiClient();
        paymentApiClient.setBasePath(address + paymentApiClient.getBasePath());
        paymentApiClient.setApiKeyPrefix("Bearer");
        paymentApiClient.setApiKey(appKey);

        network.golem.yajapi.activity.ApiClient activityApiClient = network.golem.yajapi.activity.Configuration.getDefaultApiClient();
        activityApiClient.setBasePath(address + activityApiClient.getBasePath());
        activityApiClient.setApiKeyPrefix("Bearer");
        activityApiClient.setApiKey(appKey);

        network.golem.yajapi.market.ApiClient marketApiClient = network.golem.yajapi.market.Configuration.getDefaultApiClient();
        marketApiClient.setBasePath(address + marketApiClient.getBasePath());
        marketApiClient.setApiKeyPrefix("Bearer");
        marketApiClient.setApiKey(appKey);

        done = true;
    }

    public static network.golem.yajapi.payment.apis.RequestorApi newPaymentRequestorApi() {
        initialize();
        return new network.golem.yajapi.payment.apis.RequestorApi();
    }
    public static network.golem.yajapi.activity.apis.RequestorStateApi newActivityRequestorStateApi() {
        initialize();
        return new network.golem.yajapi.activity.apis.RequestorStateApi();
    }
    public static network.golem.yajapi.activity.apis.RequestorControlApi newActivityRequestorControlApi() {
        initialize();
        return new network.golem.yajapi.activity.apis.RequestorControlApi();
    }
    public static network.golem.yajapi.market.apis.RequestorApi newMarketRequestorApi() {
        initialize();
        return new network.golem.yajapi.market.apis.RequestorApi();
    }
}
