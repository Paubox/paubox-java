# MessagesApi

All URIs are relative to *https://api.paubox.net/v1/YOUR_API_USERNAME*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getMessageReceipt**](MessagesApi.md#getMessageReceipt) | **GET** /message_receipt | Get email disposition |
| [**getMessageReceiptWithHttpInfo**](MessagesApi.md#getMessageReceiptWithHttpInfo) | **GET** /message_receipt | Get email disposition |
| [**sendBulkMessages**](MessagesApi.md#sendBulkMessages) | **POST** /bulk_messages | Send multiple email messages (batch) |
| [**sendBulkMessagesWithHttpInfo**](MessagesApi.md#sendBulkMessagesWithHttpInfo) | **POST** /bulk_messages | Send multiple email messages (batch) |
| [**sendMessage**](MessagesApi.md#sendMessage) | **POST** /messages | Send a single email message |
| [**sendMessageWithHttpInfo**](MessagesApi.md#sendMessageWithHttpInfo) | **POST** /messages | Send a single email message |



## getMessageReceipt

> MessageReceiptResponse getMessageReceipt(sourceTrackingId)

Get email disposition

Retrieve delivery status, open tracking, and click tracking information for a sent message

### Example

```java
// Import classes:
import com.paubox.ApiClient;
import com.paubox.ApiException;
import com.paubox.Configuration;
import com.paubox.auth.*;
import com.paubox.models.*;
import com.paubox.api.MessagesApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.paubox.net/v1/YOUR_API_USERNAME");
        
        // Configure API key authorization: PauboxToken
        ApiKeyAuth PauboxToken = (ApiKeyAuth) defaultClient.getAuthentication("PauboxToken");
        PauboxToken.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //PauboxToken.setApiKeyPrefix("Token");

        MessagesApi apiInstance = new MessagesApi(defaultClient);
        UUID sourceTrackingId = UUID.fromString("6e1cf9a4-7bde-4834-8200-ed424b50c8a7"); // UUID | The tracking ID returned when the message was sent
        try {
            MessageReceiptResponse result = apiInstance.getMessageReceipt(sourceTrackingId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MessagesApi#getMessageReceipt");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **sourceTrackingId** | **UUID**| The tracking ID returned when the message was sent | |

### Return type

[**MessageReceiptResponse**](MessageReceiptResponse.md)


### Authorization

[PauboxToken](../README.md#PauboxToken)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Message disposition data |  -  |
| **404** | Message not found |  -  |

## getMessageReceiptWithHttpInfo

> ApiResponse<MessageReceiptResponse> getMessageReceipt getMessageReceiptWithHttpInfo(sourceTrackingId)

Get email disposition

Retrieve delivery status, open tracking, and click tracking information for a sent message

### Example

```java
// Import classes:
import com.paubox.ApiClient;
import com.paubox.ApiException;
import com.paubox.ApiResponse;
import com.paubox.Configuration;
import com.paubox.auth.*;
import com.paubox.models.*;
import com.paubox.api.MessagesApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.paubox.net/v1/YOUR_API_USERNAME");
        
        // Configure API key authorization: PauboxToken
        ApiKeyAuth PauboxToken = (ApiKeyAuth) defaultClient.getAuthentication("PauboxToken");
        PauboxToken.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //PauboxToken.setApiKeyPrefix("Token");

        MessagesApi apiInstance = new MessagesApi(defaultClient);
        UUID sourceTrackingId = UUID.fromString("6e1cf9a4-7bde-4834-8200-ed424b50c8a7"); // UUID | The tracking ID returned when the message was sent
        try {
            ApiResponse<MessageReceiptResponse> response = apiInstance.getMessageReceiptWithHttpInfo(sourceTrackingId);
            System.out.println("Status code: " + response.getStatusCode());
            System.out.println("Response headers: " + response.getHeaders());
            System.out.println("Response body: " + response.getData());
        } catch (ApiException e) {
            System.err.println("Exception when calling MessagesApi#getMessageReceipt");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Response headers: " + e.getResponseHeaders());
            System.err.println("Reason: " + e.getResponseBody());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **sourceTrackingId** | **UUID**| The tracking ID returned when the message was sent | |

### Return type

ApiResponse<[**MessageReceiptResponse**](MessageReceiptResponse.md)>


### Authorization

[PauboxToken](../README.md#PauboxToken)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Message disposition data |  -  |
| **404** | Message not found |  -  |


## sendBulkMessages

> BulkSendResponse sendBulkMessages(bulkSendRequest)

Send multiple email messages (batch)

Sends multiple messages in one request. Paubox recommends batches of 50 or fewer. Source tracking IDs are returned in the same order as the messages array. 

### Example

```java
// Import classes:
import com.paubox.ApiClient;
import com.paubox.ApiException;
import com.paubox.Configuration;
import com.paubox.auth.*;
import com.paubox.models.*;
import com.paubox.api.MessagesApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.paubox.net/v1/YOUR_API_USERNAME");
        
        // Configure API key authorization: PauboxToken
        ApiKeyAuth PauboxToken = (ApiKeyAuth) defaultClient.getAuthentication("PauboxToken");
        PauboxToken.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //PauboxToken.setApiKeyPrefix("Token");

        MessagesApi apiInstance = new MessagesApi(defaultClient);
        BulkSendRequest bulkSendRequest = new BulkSendRequest(); // BulkSendRequest | 
        try {
            BulkSendResponse result = apiInstance.sendBulkMessages(bulkSendRequest);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MessagesApi#sendBulkMessages");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **bulkSendRequest** | [**BulkSendRequest**](BulkSendRequest.md)|  | |

### Return type

[**BulkSendResponse**](BulkSendResponse.md)


### Authorization

[PauboxToken](../README.md#PauboxToken)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Batch accepted |  -  |
| **400** | Bad request |  -  |

## sendBulkMessagesWithHttpInfo

> ApiResponse<BulkSendResponse> sendBulkMessages sendBulkMessagesWithHttpInfo(bulkSendRequest)

Send multiple email messages (batch)

Sends multiple messages in one request. Paubox recommends batches of 50 or fewer. Source tracking IDs are returned in the same order as the messages array. 

### Example

```java
// Import classes:
import com.paubox.ApiClient;
import com.paubox.ApiException;
import com.paubox.ApiResponse;
import com.paubox.Configuration;
import com.paubox.auth.*;
import com.paubox.models.*;
import com.paubox.api.MessagesApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.paubox.net/v1/YOUR_API_USERNAME");
        
        // Configure API key authorization: PauboxToken
        ApiKeyAuth PauboxToken = (ApiKeyAuth) defaultClient.getAuthentication("PauboxToken");
        PauboxToken.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //PauboxToken.setApiKeyPrefix("Token");

        MessagesApi apiInstance = new MessagesApi(defaultClient);
        BulkSendRequest bulkSendRequest = new BulkSendRequest(); // BulkSendRequest | 
        try {
            ApiResponse<BulkSendResponse> response = apiInstance.sendBulkMessagesWithHttpInfo(bulkSendRequest);
            System.out.println("Status code: " + response.getStatusCode());
            System.out.println("Response headers: " + response.getHeaders());
            System.out.println("Response body: " + response.getData());
        } catch (ApiException e) {
            System.err.println("Exception when calling MessagesApi#sendBulkMessages");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Response headers: " + e.getResponseHeaders());
            System.err.println("Reason: " + e.getResponseBody());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **bulkSendRequest** | [**BulkSendRequest**](BulkSendRequest.md)|  | |

### Return type

ApiResponse<[**BulkSendResponse**](BulkSendResponse.md)>


### Authorization

[PauboxToken](../README.md#PauboxToken)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Batch accepted |  -  |
| **400** | Bad request |  -  |


## sendMessage

> SingleSendResponse sendMessage(singleSendRequest)

Send a single email message

### Example

```java
// Import classes:
import com.paubox.ApiClient;
import com.paubox.ApiException;
import com.paubox.Configuration;
import com.paubox.auth.*;
import com.paubox.models.*;
import com.paubox.api.MessagesApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.paubox.net/v1/YOUR_API_USERNAME");
        
        // Configure API key authorization: PauboxToken
        ApiKeyAuth PauboxToken = (ApiKeyAuth) defaultClient.getAuthentication("PauboxToken");
        PauboxToken.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //PauboxToken.setApiKeyPrefix("Token");

        MessagesApi apiInstance = new MessagesApi(defaultClient);
        SingleSendRequest singleSendRequest = new SingleSendRequest(); // SingleSendRequest | 
        try {
            SingleSendResponse result = apiInstance.sendMessage(singleSendRequest);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MessagesApi#sendMessage");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **singleSendRequest** | [**SingleSendRequest**](SingleSendRequest.md)|  | |

### Return type

[**SingleSendResponse**](SingleSendResponse.md)


### Authorization

[PauboxToken](../README.md#PauboxToken)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Message accepted |  -  |
| **400** | Bad request |  -  |

## sendMessageWithHttpInfo

> ApiResponse<SingleSendResponse> sendMessage sendMessageWithHttpInfo(singleSendRequest)

Send a single email message

### Example

```java
// Import classes:
import com.paubox.ApiClient;
import com.paubox.ApiException;
import com.paubox.ApiResponse;
import com.paubox.Configuration;
import com.paubox.auth.*;
import com.paubox.models.*;
import com.paubox.api.MessagesApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.paubox.net/v1/YOUR_API_USERNAME");
        
        // Configure API key authorization: PauboxToken
        ApiKeyAuth PauboxToken = (ApiKeyAuth) defaultClient.getAuthentication("PauboxToken");
        PauboxToken.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //PauboxToken.setApiKeyPrefix("Token");

        MessagesApi apiInstance = new MessagesApi(defaultClient);
        SingleSendRequest singleSendRequest = new SingleSendRequest(); // SingleSendRequest | 
        try {
            ApiResponse<SingleSendResponse> response = apiInstance.sendMessageWithHttpInfo(singleSendRequest);
            System.out.println("Status code: " + response.getStatusCode());
            System.out.println("Response headers: " + response.getHeaders());
            System.out.println("Response body: " + response.getData());
        } catch (ApiException e) {
            System.err.println("Exception when calling MessagesApi#sendMessage");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Response headers: " + e.getResponseHeaders());
            System.err.println("Reason: " + e.getResponseBody());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **singleSendRequest** | [**SingleSendRequest**](SingleSendRequest.md)|  | |

### Return type

ApiResponse<[**SingleSendResponse**](SingleSendResponse.md)>


### Authorization

[PauboxToken](../README.md#PauboxToken)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Message accepted |  -  |
| **400** | Bad request |  -  |

