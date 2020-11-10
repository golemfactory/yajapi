package network.golem.yajapi.payment.apis;

import network.golem.yajapi.payment.ApiException;
import network.golem.yajapi.payment.ApiClient;
import network.golem.yajapi.payment.Configuration;
import network.golem.yajapi.payment.Pair;

import javax.ws.rs.core.GenericType;

import network.golem.yajapi.payment.models.Acceptance;
import network.golem.yajapi.payment.models.Account;
import network.golem.yajapi.payment.models.Allocation;
import java.math.BigDecimal;
import network.golem.yajapi.payment.models.DebitNote;
import network.golem.yajapi.payment.models.DebitNoteEvent;
import network.golem.yajapi.payment.models.ErrorMessage;
import network.golem.yajapi.payment.models.Invoice;
import network.golem.yajapi.payment.models.InvoiceEvent;
import java.time.OffsetDateTime;
import network.golem.yajapi.payment.models.Payment;
import network.golem.yajapi.payment.models.Rejection;

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
   * Accept received Debit Note.
   * Send Debit Note Accepted message to Debit Note Issuer. Trigger payment orchestration for this Debit Note (using allocated lot identified by AllocationId if any).  This is a blocking operation. It will not return until the Requestor has acknowledged accepting the Invoice or timeout has passed.  NOTE: An Accepted Debit Note cannot be Rejected later. 
   * @param body  (required)
   * @param debitNodeId  (required)
   * @param timeout How many seconds server should wait for acknowledgement from the remote party (0 means forever)  (optional, default to 60)
   * @throws ApiException if fails to make API call
   */
  public void acceptDebitNote(Acceptance body, String debitNodeId, BigDecimal timeout) throws ApiException {
    Object localVarPostBody = body;
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling acceptDebitNote");
    }
    // verify the required parameter 'debitNodeId' is set
    if (debitNodeId == null) {
      throw new ApiException(400, "Missing the required parameter 'debitNodeId' when calling acceptDebitNote");
    }
    // create path and map variables
    String localVarPath = "/requestor/debitNotes/{debitNodeId}/accept"
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
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "app_key" };

    apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
  /**
   * Accept received Invoice.
   * Send Invoice Accepted message to Invoice Issuer. Trigger payment orchestration for this Invoice (using allocated lot identified by AllocationId if any).  This is a blocking operation. It will not return until the Requestor has acknowledged rejecting the Invoice or timeout has passed.  NOTE: An Accepted Invoice cannot be Rejected later. 
   * @param body  (required)
   * @param invoiceId  (required)
   * @param timeout How many seconds server should wait for acknowledgement from the remote party (0 means forever)  (optional, default to 60)
   * @throws ApiException if fails to make API call
   */
  public void acceptInvoice(Acceptance body, String invoiceId, BigDecimal timeout) throws ApiException {
    Object localVarPostBody = body;
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling acceptInvoice");
    }
    // verify the required parameter 'invoiceId' is set
    if (invoiceId == null) {
      throw new ApiException(400, "Missing the required parameter 'invoiceId' when calling acceptInvoice");
    }
    // create path and map variables
    String localVarPath = "/requestor/invoices/{invoiceId}/accept"
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
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "app_key" };

    apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
  /**
   * Amend Allocation.
   * 
   * @param body  (required)
   * @param allocationId  (required)
   * @return Allocation
   * @throws ApiException if fails to make API call
   */
  public Allocation amendAllocation(Allocation body, String allocationId) throws ApiException {
    Object localVarPostBody = body;
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling amendAllocation");
    }
    // verify the required parameter 'allocationId' is set
    if (allocationId == null) {
      throw new ApiException(400, "Missing the required parameter 'allocationId' when calling amendAllocation");
    }
    // create path and map variables
    String localVarPath = "/requestor/allocations/{allocationId}"
      .replaceAll("\\{" + "allocationId" + "\\}", apiClient.escapeString(allocationId.toString()));

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

    GenericType<Allocation> localVarReturnType = new GenericType<Allocation>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Create Allocation.
   * Allocate funds to make sure they are not spent elsewhere.
   * @param body  (required)
   * @return Allocation
   * @throws ApiException if fails to make API call
   */
  public Allocation createAllocation(Allocation body) throws ApiException {
    Object localVarPostBody = body;
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createAllocation");
    }
    // create path and map variables
    String localVarPath = "/requestor/allocations";

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

    GenericType<Allocation> localVarReturnType = new GenericType<Allocation>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Get Allocation.
   * 
   * @param allocationId  (required)
   * @return Allocation
   * @throws ApiException if fails to make API call
   */
  public Allocation getAllocation(String allocationId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'allocationId' is set
    if (allocationId == null) {
      throw new ApiException(400, "Missing the required parameter 'allocationId' when calling getAllocation");
    }
    // create path and map variables
    String localVarPath = "/requestor/allocations/{allocationId}"
      .replaceAll("\\{" + "allocationId" + "\\}", apiClient.escapeString(allocationId.toString()));

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
   * Get Allocations.
   * 
   * @return List&lt;Allocation&gt;
   * @throws ApiException if fails to make API call
   */
  public List<Allocation> getAllocations() throws ApiException {
    Object localVarPostBody = null;
    // create path and map variables
    String localVarPath = "/requestor/allocations";

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

    GenericType<List<Allocation>> localVarReturnType = new GenericType<List<Allocation>>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Get outgoing Payment.
   * 
   * @param paymentId  (required)
   * @return Allocation
   * @throws ApiException if fails to make API call
   */
  public Allocation getOutgoingPayment(String paymentId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'paymentId' is set
    if (paymentId == null) {
      throw new ApiException(400, "Missing the required parameter 'paymentId' when calling getOutgoingPayment");
    }
    // create path and map variables
    String localVarPath = "/requestor/payments/{paymentId}"
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
   * Get outgoing Payments.
   * Payments can be treated as events and this method can be used to listen for new payments by long-polling.  If there are any payments the method will return them immediately. If there are none the method will wait until one appears or timeout passes. &#x60;laterThan&#x60; parameter can be used in order to get just the &#x27;new&#x27; payments. Setting the parameter value to the timestamp of the last processed payment ensures that no payments will go unnoticed. 
   * @param timeout How many seconds server should wait for new events (0 means it should return immediately if there are no events)  (optional, default to 0)
   * @param laterThan Show only events later than specified timeout (optional)
   * @return List&lt;Payment&gt;
   * @throws ApiException if fails to make API call
   */
  public List<Payment> getOutgoingPayments(BigDecimal timeout, OffsetDateTime laterThan) throws ApiException {
    Object localVarPostBody = null;
    // create path and map variables
    String localVarPath = "/requestor/payments";

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
   * Get Payments for Debit Note.
   * 
   * @param debitNodeId  (required)
   * @return List&lt;Payment&gt;
   * @throws ApiException if fails to make API call
   */
  public List<Payment> getPaymentsForReceivedDebitNote(String debitNodeId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'debitNodeId' is set
    if (debitNodeId == null) {
      throw new ApiException(400, "Missing the required parameter 'debitNodeId' when calling getPaymentsForReceivedDebitNote");
    }
    // create path and map variables
    String localVarPath = "/requestor/debitNotes/{debitNodeId}/payments"
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
   * Get Payments for received Invoice.
   * 
   * @param invoiceId  (required)
   * @return List&lt;Payment&gt;
   * @throws ApiException if fails to make API call
   */
  public List<Payment> getPaymentsForReceivedInvoice(String invoiceId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'invoiceId' is set
    if (invoiceId == null) {
      throw new ApiException(400, "Missing the required parameter 'invoiceId' when calling getPaymentsForReceivedInvoice");
    }
    // create path and map variables
    String localVarPath = "/requestor/invoices/{invoiceId}/payments"
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
   * Get Debit Note.
   * 
   * @param debitNodeId  (required)
   * @return DebitNote
   * @throws ApiException if fails to make API call
   */
  public DebitNote getReceivedDebitNote(String debitNodeId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'debitNodeId' is set
    if (debitNodeId == null) {
      throw new ApiException(400, "Missing the required parameter 'debitNodeId' when calling getReceivedDebitNote");
    }
    // create path and map variables
    String localVarPath = "/requestor/debitNotes/{debitNodeId}"
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
   * Get Debit Notes received by this Requestor.
   * 
   * @return List&lt;DebitNote&gt;
   * @throws ApiException if fails to make API call
   */
  public List<DebitNote> getReceivedDebitNotes() throws ApiException {
    Object localVarPostBody = null;
    // create path and map variables
    String localVarPath = "/requestor/debitNotes";

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
  public Invoice getReceivedInvoice(String invoiceId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'invoiceId' is set
    if (invoiceId == null) {
      throw new ApiException(400, "Missing the required parameter 'invoiceId' when calling getReceivedInvoice");
    }
    // create path and map variables
    String localVarPath = "/requestor/invoices/{invoiceId}"
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
   * Get Invoices received by this Requestor.
   * 
   * @return List&lt;Invoice&gt;
   * @throws ApiException if fails to make API call
   */
  public List<Invoice> getReceivedInvoices() throws ApiException {
    Object localVarPostBody = null;
    // create path and map variables
    String localVarPath = "/requestor/invoices";

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
   * Get Debit Note events.
   * Listen for Debit Note-related events using long-polling. If there are any events the method will return them immediately. If there are none the method will wait until one appears or timeout passes. &#x60;laterThan&#x60; parameter can be used in order to get just the &#x27;new&#x27; events. Setting the parameter value to the timestamp of the last processed event ensures that no events will go unnoticed. 
   * @param timeout How many seconds server should wait for new events (0 means it should return immediately if there are no events)  (optional, default to 0)
   * @param laterThan Show only events later than specified timeout (optional)
   * @return List&lt;DebitNoteEvent&gt;
   * @throws ApiException if fails to make API call
   */
  public List<DebitNoteEvent> getRequestorDebitNoteEvents(BigDecimal timeout, OffsetDateTime laterThan) throws ApiException {
    Object localVarPostBody = null;
    // create path and map variables
    String localVarPath = "/requestor/debitNoteEvents";

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

    GenericType<List<DebitNoteEvent>> localVarReturnType = new GenericType<List<DebitNoteEvent>>() {};
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
  public List<InvoiceEvent> getRequestorInvoiceEvents(BigDecimal timeout, OffsetDateTime laterThan) throws ApiException {
    Object localVarPostBody = null;
    // create path and map variables
    String localVarPath = "/requestor/invoiceEvents";

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
   * Get available accounts for sending payments.
   * 
   * @return List&lt;Account&gt;
   * @throws ApiException if fails to make API call
   */
  public List<Account> getSendAccounts() throws ApiException {
    Object localVarPostBody = null;
    // create path and map variables
    String localVarPath = "/requestor/accounts";

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
   * Reject received Debit Note.
   * Send Debit Note Rejected message to Invoice Issuer. Notification of rejection is signalling that Requestor does not accept the Debit Note (for some reason).  This is a blocking operation. It will not return until the Requestor has acknowledged rejecting the Invoice or timeout has passed.  NOTE: A Rejected Debit Note can be Accepted subsequently (e.g. as a result of some arbitrage). 
   * @param body  (required)
   * @param debitNodeId  (required)
   * @param timeout How many seconds server should wait for acknowledgement from the remote party (0 means forever)  (optional, default to 60)
   * @throws ApiException if fails to make API call
   */
  public void rejectDebitNote(Rejection body, String debitNodeId, BigDecimal timeout) throws ApiException {
    Object localVarPostBody = body;
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling rejectDebitNote");
    }
    // verify the required parameter 'debitNodeId' is set
    if (debitNodeId == null) {
      throw new ApiException(400, "Missing the required parameter 'debitNodeId' when calling rejectDebitNote");
    }
    // create path and map variables
    String localVarPath = "/requestor/debitNotes/{debitNodeId}/reject"
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
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "app_key" };

    apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
  /**
   * Reject received Invoice.
   * Send Invoice Rejected message to Invoice Issuer. Notification of rejection is signalling that Requestor does not accept Invoice (for some reason).  This is a blocking operation. It will not return until the Requestor has acknowledged rejecting the Invoice or timeout has passed.  NOTE: A Rejected Invoice can be Accepted subsequently (e.g. as a result of some arbitrage). 
   * @param body  (required)
   * @param invoiceId  (required)
   * @param timeout How many seconds server should wait for acknowledgement from the remote party (0 means forever)  (optional, default to 60)
   * @throws ApiException if fails to make API call
   */
  public void rejectInvoice(Rejection body, String invoiceId, BigDecimal timeout) throws ApiException {
    Object localVarPostBody = body;
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling rejectInvoice");
    }
    // verify the required parameter 'invoiceId' is set
    if (invoiceId == null) {
      throw new ApiException(400, "Missing the required parameter 'invoiceId' when calling rejectInvoice");
    }
    // create path and map variables
    String localVarPath = "/requestor/invoices/{invoiceId}/reject"
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
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "app_key" };

    apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
  /**
   * Release Allocation.
   * The Allocation of amount is released. Note that this operation releases currently allocated amount (which may have been reduced by subsequent Invoice Payments).  If the Allocation was connected with a Deposit the release amount from Deposit shall be marked as pending to be paid back to Requestor - and eventually will be paid back, unless a subsequent Allocation with Deposit is made. The Payment Platform implementations may optimize unnecessary fund transfers (i.e. will not pay back the Deposit if released funds can be assigned to a new Allocation with Deposit). 
   * @param allocationId  (required)
   * @throws ApiException if fails to make API call
   */
  public void releaseAllocation(String allocationId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'allocationId' is set
    if (allocationId == null) {
      throw new ApiException(400, "Missing the required parameter 'allocationId' when calling releaseAllocation");
    }
    // create path and map variables
    String localVarPath = "/requestor/allocations/{allocationId}"
      .replaceAll("\\{" + "allocationId" + "\\}", apiClient.escapeString(allocationId.toString()));

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
