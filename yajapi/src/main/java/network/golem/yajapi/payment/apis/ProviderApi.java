package network.golem.yajapi.payment.apis;

import network.golem.yajapi.payment.ApiException;
import network.golem.yajapi.payment.ApiClient;
import network.golem.yajapi.payment.Configuration;
import network.golem.yajapi.payment.Pair;

import javax.ws.rs.core.GenericType;

import network.golem.yajapi.payment.models.Account;
import network.golem.yajapi.payment.models.Allocation;
import java.math.BigDecimal;
import network.golem.yajapi.payment.models.DebitNote;
import network.golem.yajapi.payment.models.ErrorMessage;
import network.golem.yajapi.payment.models.Invoice;
import network.golem.yajapi.payment.models.InvoiceEvent;
import java.time.OffsetDateTime;
import network.golem.yajapi.payment.models.Payment;

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
   * Cancel Debit Note.
   * This is a blocking operation. It will not return until the Requestor has acknowledged cancelling the Debit Note or timeout has passed. The Requestor may refuse to cancel the Debit Note if they have already paid it. 
   * @param debitNodeId  (required)
   * @param timeout How many seconds server should wait for acknowledgement from the remote party (0 means forever)  (optional, default to 60)
   * @throws ApiException if fails to make API call
   */
  public void cancelDebitNote(String debitNodeId, BigDecimal timeout) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'debitNodeId' is set
    if (debitNodeId == null) {
      throw new ApiException(400, "Missing the required parameter 'debitNodeId' when calling cancelDebitNote");
    }
    // create path and map variables
    String localVarPath = "/provider/debitNotes/{debitNodeId}/cancel"
      .replaceAll("\\{" + "debitNodeId" + "\\}", apiClient.escapeString(debitNodeId.toString()));

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

    apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
  /**
   * Cancel Invoice.
   * This is a blocking operation. It will not return until the Requestor has acknowledged cancelling the Invoice or timeout has passed. The Requestor may refuse to cancel the Invoice if they have already paid it. 
   * @param invoiceId  (required)
   * @param timeout How many seconds server should wait for acknowledgement from the remote party (0 means forever)  (optional, default to 60)
   * @throws ApiException if fails to make API call
   */
  public void cancelInvoice(String invoiceId, BigDecimal timeout) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'invoiceId' is set
    if (invoiceId == null) {
      throw new ApiException(400, "Missing the required parameter 'invoiceId' when calling cancelInvoice");
    }
    // create path and map variables
    String localVarPath = "/provider/invoices/{invoiceId}/cancel"
      .replaceAll("\\{" + "invoiceId" + "\\}", apiClient.escapeString(invoiceId.toString()));

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

    apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
  /**
   * Get incoming Payment.
   * 
   * @param paymentId  (required)
   * @return Allocation
   * @throws ApiException if fails to make API call
   */
  public Allocation getIncomingPayment(String paymentId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'paymentId' is set
    if (paymentId == null) {
      throw new ApiException(400, "Missing the required parameter 'paymentId' when calling getIncomingPayment");
    }
    // create path and map variables
    String localVarPath = "/provider/payments/{paymentId}"
      .replaceAll("\\{" + "paymentId" + "\\}", apiClient.escapeString(paymentId.toString()));

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

    GenericType<Allocation> localVarReturnType = new GenericType<Allocation>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Get incoming Payments.
   * Payments can be treated as events and this method can be used to listen for new payments by long-polling.  If there are any payments the method will return them immediately. If there are none the method will wait until one appears or timeout passes. &#x60;laterThan&#x60; parameter can be used in order to get just the &#x27;new&#x27; payments. Setting the parameter value to the timestamp of the last processed payment ensures that no payments will go unnoticed. 
   * @param timeout How many seconds server should wait for new events (0 means it should return immediately if there are no events)  (optional, default to 0)
   * @param laterThan Show only events later than specified timeout (optional)
   * @return List&lt;Payment&gt;
   * @throws ApiException if fails to make API call
   */
  public List<Payment> getIncomingPayments(BigDecimal timeout, OffsetDateTime laterThan) throws ApiException {
    Object localVarPostBody = null;
    // create path and map variables
    String localVarPath = "/provider/payments";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "timeout", timeout));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "laterThan", laterThan));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "app_key" };

    GenericType<List<Payment>> localVarReturnType = new GenericType<List<Payment>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Get Debit Note.
   * 
   * @param debitNodeId  (required)
   * @return DebitNote
   * @throws ApiException if fails to make API call
   */
  public DebitNote getIssuedDebitNote(String debitNodeId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'debitNodeId' is set
    if (debitNodeId == null) {
      throw new ApiException(400, "Missing the required parameter 'debitNodeId' when calling getIssuedDebitNote");
    }
    // create path and map variables
    String localVarPath = "/provider/debitNotes/{debitNodeId}"
      .replaceAll("\\{" + "debitNodeId" + "\\}", apiClient.escapeString(debitNodeId.toString()));

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

    GenericType<DebitNote> localVarReturnType = new GenericType<DebitNote>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Get Debit Notes issued by this Provider.
   * 
   * @return List&lt;DebitNote&gt;
   * @throws ApiException if fails to make API call
   */
  public List<DebitNote> getIssuedDebitNotes() throws ApiException {
    Object localVarPostBody = null;
    // create path and map variables
    String localVarPath = "/provider/debitNotes";

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

    GenericType<List<DebitNote>> localVarReturnType = new GenericType<List<DebitNote>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Get Invoice.
   * 
   * @param invoiceId  (required)
   * @return Invoice
   * @throws ApiException if fails to make API call
   */
  public Invoice getIssuedInvoice(String invoiceId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'invoiceId' is set
    if (invoiceId == null) {
      throw new ApiException(400, "Missing the required parameter 'invoiceId' when calling getIssuedInvoice");
    }
    // create path and map variables
    String localVarPath = "/provider/invoices/{invoiceId}"
      .replaceAll("\\{" + "invoiceId" + "\\}", apiClient.escapeString(invoiceId.toString()));

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

    GenericType<Invoice> localVarReturnType = new GenericType<Invoice>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Get Invoices issued by this Provider.
   * 
   * @return List&lt;Invoice&gt;
   * @throws ApiException if fails to make API call
   */
  public List<Invoice> getIssuedInvoices() throws ApiException {
    Object localVarPostBody = null;
    // create path and map variables
    String localVarPath = "/provider/invoices";

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

    GenericType<List<Invoice>> localVarReturnType = new GenericType<List<Invoice>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Get Payments for Debit Note.
   * 
   * @param debitNodeId  (required)
   * @return List&lt;Payment&gt;
   * @throws ApiException if fails to make API call
   */
  public List<Payment> getPaymentsForIssuedDebitNote(String debitNodeId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'debitNodeId' is set
    if (debitNodeId == null) {
      throw new ApiException(400, "Missing the required parameter 'debitNodeId' when calling getPaymentsForIssuedDebitNote");
    }
    // create path and map variables
    String localVarPath = "/provider/debitNotes/{debitNodeId}/payments"
      .replaceAll("\\{" + "debitNodeId" + "\\}", apiClient.escapeString(debitNodeId.toString()));

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

    GenericType<List<Payment>> localVarReturnType = new GenericType<List<Payment>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Get Payments for issued Invoice.
   * 
   * @param invoiceId  (required)
   * @return List&lt;Payment&gt;
   * @throws ApiException if fails to make API call
   */
  public List<Payment> getPaymentsForIssuedInvoice(String invoiceId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'invoiceId' is set
    if (invoiceId == null) {
      throw new ApiException(400, "Missing the required parameter 'invoiceId' when calling getPaymentsForIssuedInvoice");
    }
    // create path and map variables
    String localVarPath = "/provider/invoices/{invoiceId}/payments"
      .replaceAll("\\{" + "invoiceId" + "\\}", apiClient.escapeString(invoiceId.toString()));

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

    GenericType<List<Payment>> localVarReturnType = new GenericType<List<Payment>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Get Debit Note events.
   * Listen for Debit Note-related events using long-polling. If there are any events the method will return them immediately. If there are none the method will wait until one appears or timeout passes. &#x60;laterThan&#x60; parameter can be used in order to get just the &#x27;new&#x27; events. Setting the parameter value to the timestamp of the last processed event ensures that no events will go unnoticed. 
   * @param timeout How many seconds server should wait for new events (0 means it should return immediately if there are no events)  (optional, default to 0)
   * @param laterThan Show only events later than specified timeout (optional)
   * @return List&lt;InvoiceEvent&gt;
   * @throws ApiException if fails to make API call
   */
  public List<InvoiceEvent> getProviderDebitNoteEvents(BigDecimal timeout, OffsetDateTime laterThan) throws ApiException {
    Object localVarPostBody = null;
    // create path and map variables
    String localVarPath = "/provider/debitNoteEvents";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "timeout", timeout));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "laterThan", laterThan));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "app_key" };

    GenericType<List<InvoiceEvent>> localVarReturnType = new GenericType<List<InvoiceEvent>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Get Invoice events.
   * Listen for Invoice-related events using long-polling. If there are any events the method will return them immediately. If there are none the method will wait until one appears or timeout passes. &#x60;laterThan&#x60; parameter can be used in order to get just the &#x27;new&#x27; events. Setting the parameter value to the timestamp of the last processed event ensures that no events will go unnoticed. 
   * @param timeout How many seconds server should wait for new events (0 means it should return immediately if there are no events)  (optional, default to 0)
   * @param laterThan Show only events later than specified timeout (optional)
   * @return List&lt;InvoiceEvent&gt;
   * @throws ApiException if fails to make API call
   */
  public List<InvoiceEvent> getProviderInvoiceEvents(BigDecimal timeout, OffsetDateTime laterThan) throws ApiException {
    Object localVarPostBody = null;
    // create path and map variables
    String localVarPath = "/provider/invoiceEvents";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "timeout", timeout));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "laterThan", laterThan));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "app_key" };

    GenericType<List<InvoiceEvent>> localVarReturnType = new GenericType<List<InvoiceEvent>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Get available accounts for receiving payments.
   * 
   * @return List&lt;Account&gt;
   * @throws ApiException if fails to make API call
   */
  public List<Account> getReceiveAccounts() throws ApiException {
    Object localVarPostBody = null;
    // create path and map variables
    String localVarPath = "/provider/accounts";

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

    GenericType<List<Account>> localVarReturnType = new GenericType<List<Account>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Issue a Debit Note.
   * 
   * @param body  (required)
   * @return DebitNote
   * @throws ApiException if fails to make API call
   */
  public DebitNote issueDebitNote(DebitNote body) throws ApiException {
    Object localVarPostBody = body;
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling issueDebitNote");
    }
    // create path and map variables
    String localVarPath = "/provider/debitNotes";

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

    GenericType<DebitNote> localVarReturnType = new GenericType<DebitNote>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Issue an Invoice.
   * 
   * @param body  (required)
   * @return Invoice
   * @throws ApiException if fails to make API call
   */
  public Invoice issueInvoice(Invoice body) throws ApiException {
    Object localVarPostBody = body;
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling issueInvoice");
    }
    // create path and map variables
    String localVarPath = "/provider/invoices";

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

    GenericType<Invoice> localVarReturnType = new GenericType<Invoice>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Send Debit Note to Requestor.
   * This is a blocking operation. It will not return until the Requestor has acknowledged receiving the Debit Note or timeout has passed. 
   * @param debitNodeId  (required)
   * @param timeout How many seconds server should wait for acknowledgement from the remote party (0 means forever)  (optional, default to 60)
   * @throws ApiException if fails to make API call
   */
  public void sendDebitNote(String debitNodeId, BigDecimal timeout) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'debitNodeId' is set
    if (debitNodeId == null) {
      throw new ApiException(400, "Missing the required parameter 'debitNodeId' when calling sendDebitNote");
    }
    // create path and map variables
    String localVarPath = "/provider/debitNotes/{debitNodeId}/send"
      .replaceAll("\\{" + "debitNodeId" + "\\}", apiClient.escapeString(debitNodeId.toString()));

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

    apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
  /**
   * Send Invoice to Requestor.
   * This is a blocking operation. It will not return until the Requestor has acknowledged receiving the Invoice or timeout has passed. 
   * @param invoiceId  (required)
   * @param timeout How many seconds server should wait for acknowledgement from the remote party (0 means forever)  (optional, default to 60)
   * @throws ApiException if fails to make API call
   */
  public void sendInvoice(String invoiceId, BigDecimal timeout) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'invoiceId' is set
    if (invoiceId == null) {
      throw new ApiException(400, "Missing the required parameter 'invoiceId' when calling sendInvoice");
    }
    // create path and map variables
    String localVarPath = "/provider/invoices/{invoiceId}/send"
      .replaceAll("\\{" + "invoiceId" + "\\}", apiClient.escapeString(invoiceId.toString()));

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

    apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
}
