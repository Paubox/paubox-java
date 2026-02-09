# DynamicTemplatesApi

All URIs are relative to *https://api.paubox.net/v1/YOUR_API_USERNAME*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createDynamicTemplate**](DynamicTemplatesApi.md#createDynamicTemplate) | **POST** /dynamic_templates | Create a dynamic template |
| [**createDynamicTemplateWithHttpInfo**](DynamicTemplatesApi.md#createDynamicTemplateWithHttpInfo) | **POST** /dynamic_templates | Create a dynamic template |
| [**deleteDynamicTemplate**](DynamicTemplatesApi.md#deleteDynamicTemplate) | **DELETE** /dynamic_templates/{id} | Delete a dynamic template |
| [**deleteDynamicTemplateWithHttpInfo**](DynamicTemplatesApi.md#deleteDynamicTemplateWithHttpInfo) | **DELETE** /dynamic_templates/{id} | Delete a dynamic template |
| [**getDynamicTemplate**](DynamicTemplatesApi.md#getDynamicTemplate) | **GET** /dynamic_templates/{id} | Get a dynamic template |
| [**getDynamicTemplateWithHttpInfo**](DynamicTemplatesApi.md#getDynamicTemplateWithHttpInfo) | **GET** /dynamic_templates/{id} | Get a dynamic template |
| [**listDynamicTemplates**](DynamicTemplatesApi.md#listDynamicTemplates) | **GET** /dynamic_templates | List all dynamic templates |
| [**listDynamicTemplatesWithHttpInfo**](DynamicTemplatesApi.md#listDynamicTemplatesWithHttpInfo) | **GET** /dynamic_templates | List all dynamic templates |
| [**sendTemplatedMessage**](DynamicTemplatesApi.md#sendTemplatedMessage) | **POST** /templated_messages | Send a dynamically templated message |
| [**sendTemplatedMessageWithHttpInfo**](DynamicTemplatesApi.md#sendTemplatedMessageWithHttpInfo) | **POST** /templated_messages | Send a dynamically templated message |
| [**updateDynamicTemplate**](DynamicTemplatesApi.md#updateDynamicTemplate) | **PATCH** /dynamic_templates/{id} | Update a dynamic template |
| [**updateDynamicTemplateWithHttpInfo**](DynamicTemplatesApi.md#updateDynamicTemplateWithHttpInfo) | **PATCH** /dynamic_templates/{id} | Update a dynamic template |



## createDynamicTemplate

> DynamicTemplateResponse createDynamicTemplate(dataName, dataBody)

Create a dynamic template

Upload a new Handlebars template for dynamic content generation

### Example

```java
// Import classes:
import com.paubox.ApiClient;
import com.paubox.ApiException;
import com.paubox.Configuration;
import com.paubox.auth.*;
import com.paubox.models.*;
import com.paubox.api.DynamicTemplatesApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.paubox.net/v1/YOUR_API_USERNAME");
        
        // Configure API key authorization: PauboxToken
        ApiKeyAuth PauboxToken = (ApiKeyAuth) defaultClient.getAuthentication("PauboxToken");
        PauboxToken.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //PauboxToken.setApiKeyPrefix("Token");

        DynamicTemplatesApi apiInstance = new DynamicTemplatesApi(defaultClient);
        String dataName = "dataName_example"; // String | Name for the template
        File dataBody = new File("/path/to/file"); // File | Handlebars template file (.hbs)
        try {
            DynamicTemplateResponse result = apiInstance.createDynamicTemplate(dataName, dataBody);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DynamicTemplatesApi#createDynamicTemplate");
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
| **dataName** | **String**| Name for the template | |
| **dataBody** | **File**| Handlebars template file (.hbs) | |

### Return type

[**DynamicTemplateResponse**](DynamicTemplateResponse.md)


### Authorization

[PauboxToken](../README.md#PauboxToken)

### HTTP request headers

- **Content-Type**: multipart/form-data
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Template created successfully |  -  |
| **400** | Bad request |  -  |

## createDynamicTemplateWithHttpInfo

> ApiResponse<DynamicTemplateResponse> createDynamicTemplate createDynamicTemplateWithHttpInfo(dataName, dataBody)

Create a dynamic template

Upload a new Handlebars template for dynamic content generation

### Example

```java
// Import classes:
import com.paubox.ApiClient;
import com.paubox.ApiException;
import com.paubox.ApiResponse;
import com.paubox.Configuration;
import com.paubox.auth.*;
import com.paubox.models.*;
import com.paubox.api.DynamicTemplatesApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.paubox.net/v1/YOUR_API_USERNAME");
        
        // Configure API key authorization: PauboxToken
        ApiKeyAuth PauboxToken = (ApiKeyAuth) defaultClient.getAuthentication("PauboxToken");
        PauboxToken.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //PauboxToken.setApiKeyPrefix("Token");

        DynamicTemplatesApi apiInstance = new DynamicTemplatesApi(defaultClient);
        String dataName = "dataName_example"; // String | Name for the template
        File dataBody = new File("/path/to/file"); // File | Handlebars template file (.hbs)
        try {
            ApiResponse<DynamicTemplateResponse> response = apiInstance.createDynamicTemplateWithHttpInfo(dataName, dataBody);
            System.out.println("Status code: " + response.getStatusCode());
            System.out.println("Response headers: " + response.getHeaders());
            System.out.println("Response body: " + response.getData());
        } catch (ApiException e) {
            System.err.println("Exception when calling DynamicTemplatesApi#createDynamicTemplate");
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
| **dataName** | **String**| Name for the template | |
| **dataBody** | **File**| Handlebars template file (.hbs) | |

### Return type

ApiResponse<[**DynamicTemplateResponse**](DynamicTemplateResponse.md)>


### Authorization

[PauboxToken](../README.md#PauboxToken)

### HTTP request headers

- **Content-Type**: multipart/form-data
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Template created successfully |  -  |
| **400** | Bad request |  -  |


## deleteDynamicTemplate

> DeleteDynamicTemplate200Response deleteDynamicTemplate(id)

Delete a dynamic template

Delete a specific dynamic template by ID

### Example

```java
// Import classes:
import com.paubox.ApiClient;
import com.paubox.ApiException;
import com.paubox.Configuration;
import com.paubox.auth.*;
import com.paubox.models.*;
import com.paubox.api.DynamicTemplatesApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.paubox.net/v1/YOUR_API_USERNAME");
        
        // Configure API key authorization: PauboxToken
        ApiKeyAuth PauboxToken = (ApiKeyAuth) defaultClient.getAuthentication("PauboxToken");
        PauboxToken.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //PauboxToken.setApiKeyPrefix("Token");

        DynamicTemplatesApi apiInstance = new DynamicTemplatesApi(defaultClient);
        String id = "id_example"; // String | Template ID to delete
        try {
            DeleteDynamicTemplate200Response result = apiInstance.deleteDynamicTemplate(id);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DynamicTemplatesApi#deleteDynamicTemplate");
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
| **id** | **String**| Template ID to delete | |

### Return type

[**DeleteDynamicTemplate200Response**](DeleteDynamicTemplate200Response.md)


### Authorization

[PauboxToken](../README.md#PauboxToken)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Template deleted successfully |  -  |
| **404** | Template not found |  -  |

## deleteDynamicTemplateWithHttpInfo

> ApiResponse<DeleteDynamicTemplate200Response> deleteDynamicTemplate deleteDynamicTemplateWithHttpInfo(id)

Delete a dynamic template

Delete a specific dynamic template by ID

### Example

```java
// Import classes:
import com.paubox.ApiClient;
import com.paubox.ApiException;
import com.paubox.ApiResponse;
import com.paubox.Configuration;
import com.paubox.auth.*;
import com.paubox.models.*;
import com.paubox.api.DynamicTemplatesApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.paubox.net/v1/YOUR_API_USERNAME");
        
        // Configure API key authorization: PauboxToken
        ApiKeyAuth PauboxToken = (ApiKeyAuth) defaultClient.getAuthentication("PauboxToken");
        PauboxToken.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //PauboxToken.setApiKeyPrefix("Token");

        DynamicTemplatesApi apiInstance = new DynamicTemplatesApi(defaultClient);
        String id = "id_example"; // String | Template ID to delete
        try {
            ApiResponse<DeleteDynamicTemplate200Response> response = apiInstance.deleteDynamicTemplateWithHttpInfo(id);
            System.out.println("Status code: " + response.getStatusCode());
            System.out.println("Response headers: " + response.getHeaders());
            System.out.println("Response body: " + response.getData());
        } catch (ApiException e) {
            System.err.println("Exception when calling DynamicTemplatesApi#deleteDynamicTemplate");
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
| **id** | **String**| Template ID to delete | |

### Return type

ApiResponse<[**DeleteDynamicTemplate200Response**](DeleteDynamicTemplate200Response.md)>


### Authorization

[PauboxToken](../README.md#PauboxToken)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Template deleted successfully |  -  |
| **404** | Template not found |  -  |


## getDynamicTemplate

> DynamicTemplateResponse getDynamicTemplate(id)

Get a dynamic template

Retrieve a specific dynamic template by ID

### Example

```java
// Import classes:
import com.paubox.ApiClient;
import com.paubox.ApiException;
import com.paubox.Configuration;
import com.paubox.auth.*;
import com.paubox.models.*;
import com.paubox.api.DynamicTemplatesApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.paubox.net/v1/YOUR_API_USERNAME");
        
        // Configure API key authorization: PauboxToken
        ApiKeyAuth PauboxToken = (ApiKeyAuth) defaultClient.getAuthentication("PauboxToken");
        PauboxToken.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //PauboxToken.setApiKeyPrefix("Token");

        DynamicTemplatesApi apiInstance = new DynamicTemplatesApi(defaultClient);
        String id = "id_example"; // String | Template ID
        try {
            DynamicTemplateResponse result = apiInstance.getDynamicTemplate(id);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DynamicTemplatesApi#getDynamicTemplate");
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
| **id** | **String**| Template ID | |

### Return type

[**DynamicTemplateResponse**](DynamicTemplateResponse.md)


### Authorization

[PauboxToken](../README.md#PauboxToken)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Template details |  -  |
| **404** | Template not found |  -  |

## getDynamicTemplateWithHttpInfo

> ApiResponse<DynamicTemplateResponse> getDynamicTemplate getDynamicTemplateWithHttpInfo(id)

Get a dynamic template

Retrieve a specific dynamic template by ID

### Example

```java
// Import classes:
import com.paubox.ApiClient;
import com.paubox.ApiException;
import com.paubox.ApiResponse;
import com.paubox.Configuration;
import com.paubox.auth.*;
import com.paubox.models.*;
import com.paubox.api.DynamicTemplatesApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.paubox.net/v1/YOUR_API_USERNAME");
        
        // Configure API key authorization: PauboxToken
        ApiKeyAuth PauboxToken = (ApiKeyAuth) defaultClient.getAuthentication("PauboxToken");
        PauboxToken.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //PauboxToken.setApiKeyPrefix("Token");

        DynamicTemplatesApi apiInstance = new DynamicTemplatesApi(defaultClient);
        String id = "id_example"; // String | Template ID
        try {
            ApiResponse<DynamicTemplateResponse> response = apiInstance.getDynamicTemplateWithHttpInfo(id);
            System.out.println("Status code: " + response.getStatusCode());
            System.out.println("Response headers: " + response.getHeaders());
            System.out.println("Response body: " + response.getData());
        } catch (ApiException e) {
            System.err.println("Exception when calling DynamicTemplatesApi#getDynamicTemplate");
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
| **id** | **String**| Template ID | |

### Return type

ApiResponse<[**DynamicTemplateResponse**](DynamicTemplateResponse.md)>


### Authorization

[PauboxToken](../README.md#PauboxToken)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Template details |  -  |
| **404** | Template not found |  -  |


## listDynamicTemplates

> DynamicTemplateListResponse listDynamicTemplates()

List all dynamic templates

Retrieve all dynamic templates for your organization

### Example

```java
// Import classes:
import com.paubox.ApiClient;
import com.paubox.ApiException;
import com.paubox.Configuration;
import com.paubox.auth.*;
import com.paubox.models.*;
import com.paubox.api.DynamicTemplatesApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.paubox.net/v1/YOUR_API_USERNAME");
        
        // Configure API key authorization: PauboxToken
        ApiKeyAuth PauboxToken = (ApiKeyAuth) defaultClient.getAuthentication("PauboxToken");
        PauboxToken.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //PauboxToken.setApiKeyPrefix("Token");

        DynamicTemplatesApi apiInstance = new DynamicTemplatesApi(defaultClient);
        try {
            DynamicTemplateListResponse result = apiInstance.listDynamicTemplates();
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DynamicTemplatesApi#listDynamicTemplates");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters

This endpoint does not need any parameter.

### Return type

[**DynamicTemplateListResponse**](DynamicTemplateListResponse.md)


### Authorization

[PauboxToken](../README.md#PauboxToken)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | List of templates |  -  |
| **400** | Bad request |  -  |

## listDynamicTemplatesWithHttpInfo

> ApiResponse<DynamicTemplateListResponse> listDynamicTemplates listDynamicTemplatesWithHttpInfo()

List all dynamic templates

Retrieve all dynamic templates for your organization

### Example

```java
// Import classes:
import com.paubox.ApiClient;
import com.paubox.ApiException;
import com.paubox.ApiResponse;
import com.paubox.Configuration;
import com.paubox.auth.*;
import com.paubox.models.*;
import com.paubox.api.DynamicTemplatesApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.paubox.net/v1/YOUR_API_USERNAME");
        
        // Configure API key authorization: PauboxToken
        ApiKeyAuth PauboxToken = (ApiKeyAuth) defaultClient.getAuthentication("PauboxToken");
        PauboxToken.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //PauboxToken.setApiKeyPrefix("Token");

        DynamicTemplatesApi apiInstance = new DynamicTemplatesApi(defaultClient);
        try {
            ApiResponse<DynamicTemplateListResponse> response = apiInstance.listDynamicTemplatesWithHttpInfo();
            System.out.println("Status code: " + response.getStatusCode());
            System.out.println("Response headers: " + response.getHeaders());
            System.out.println("Response body: " + response.getData());
        } catch (ApiException e) {
            System.err.println("Exception when calling DynamicTemplatesApi#listDynamicTemplates");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Response headers: " + e.getResponseHeaders());
            System.err.println("Reason: " + e.getResponseBody());
            e.printStackTrace();
        }
    }
}
```

### Parameters

This endpoint does not need any parameter.

### Return type

ApiResponse<[**DynamicTemplateListResponse**](DynamicTemplateListResponse.md)>


### Authorization

[PauboxToken](../README.md#PauboxToken)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | List of templates |  -  |
| **400** | Bad request |  -  |


## sendTemplatedMessage

> SingleSendResponse sendTemplatedMessage(templatedMessageRequest)

Send a dynamically templated message

Send an email using a dynamic template with variable substitution

### Example

```java
// Import classes:
import com.paubox.ApiClient;
import com.paubox.ApiException;
import com.paubox.Configuration;
import com.paubox.auth.*;
import com.paubox.models.*;
import com.paubox.api.DynamicTemplatesApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.paubox.net/v1/YOUR_API_USERNAME");
        
        // Configure API key authorization: PauboxToken
        ApiKeyAuth PauboxToken = (ApiKeyAuth) defaultClient.getAuthentication("PauboxToken");
        PauboxToken.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //PauboxToken.setApiKeyPrefix("Token");

        DynamicTemplatesApi apiInstance = new DynamicTemplatesApi(defaultClient);
        TemplatedMessageRequest templatedMessageRequest = new TemplatedMessageRequest(); // TemplatedMessageRequest | 
        try {
            SingleSendResponse result = apiInstance.sendTemplatedMessage(templatedMessageRequest);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DynamicTemplatesApi#sendTemplatedMessage");
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
| **templatedMessageRequest** | [**TemplatedMessageRequest**](TemplatedMessageRequest.md)|  | |

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
| **200** | Templated message sent successfully |  -  |
| **400** | Bad request |  -  |

## sendTemplatedMessageWithHttpInfo

> ApiResponse<SingleSendResponse> sendTemplatedMessage sendTemplatedMessageWithHttpInfo(templatedMessageRequest)

Send a dynamically templated message

Send an email using a dynamic template with variable substitution

### Example

```java
// Import classes:
import com.paubox.ApiClient;
import com.paubox.ApiException;
import com.paubox.ApiResponse;
import com.paubox.Configuration;
import com.paubox.auth.*;
import com.paubox.models.*;
import com.paubox.api.DynamicTemplatesApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.paubox.net/v1/YOUR_API_USERNAME");
        
        // Configure API key authorization: PauboxToken
        ApiKeyAuth PauboxToken = (ApiKeyAuth) defaultClient.getAuthentication("PauboxToken");
        PauboxToken.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //PauboxToken.setApiKeyPrefix("Token");

        DynamicTemplatesApi apiInstance = new DynamicTemplatesApi(defaultClient);
        TemplatedMessageRequest templatedMessageRequest = new TemplatedMessageRequest(); // TemplatedMessageRequest | 
        try {
            ApiResponse<SingleSendResponse> response = apiInstance.sendTemplatedMessageWithHttpInfo(templatedMessageRequest);
            System.out.println("Status code: " + response.getStatusCode());
            System.out.println("Response headers: " + response.getHeaders());
            System.out.println("Response body: " + response.getData());
        } catch (ApiException e) {
            System.err.println("Exception when calling DynamicTemplatesApi#sendTemplatedMessage");
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
| **templatedMessageRequest** | [**TemplatedMessageRequest**](TemplatedMessageRequest.md)|  | |

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
| **200** | Templated message sent successfully |  -  |
| **400** | Bad request |  -  |


## updateDynamicTemplate

> DynamicTemplateResponse updateDynamicTemplate(id, dataName, dataBody)

Update a dynamic template

Update an existing Handlebars template

### Example

```java
// Import classes:
import com.paubox.ApiClient;
import com.paubox.ApiException;
import com.paubox.Configuration;
import com.paubox.auth.*;
import com.paubox.models.*;
import com.paubox.api.DynamicTemplatesApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.paubox.net/v1/YOUR_API_USERNAME");
        
        // Configure API key authorization: PauboxToken
        ApiKeyAuth PauboxToken = (ApiKeyAuth) defaultClient.getAuthentication("PauboxToken");
        PauboxToken.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //PauboxToken.setApiKeyPrefix("Token");

        DynamicTemplatesApi apiInstance = new DynamicTemplatesApi(defaultClient);
        String id = "id_example"; // String | Template ID to update
        String dataName = "dataName_example"; // String | Updated name for the template
        File dataBody = new File("/path/to/file"); // File | Updated Handlebars template file (.hbs)
        try {
            DynamicTemplateResponse result = apiInstance.updateDynamicTemplate(id, dataName, dataBody);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DynamicTemplatesApi#updateDynamicTemplate");
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
| **id** | **String**| Template ID to update | |
| **dataName** | **String**| Updated name for the template | [optional] |
| **dataBody** | **File**| Updated Handlebars template file (.hbs) | [optional] |

### Return type

[**DynamicTemplateResponse**](DynamicTemplateResponse.md)


### Authorization

[PauboxToken](../README.md#PauboxToken)

### HTTP request headers

- **Content-Type**: multipart/form-data
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Template updated successfully |  -  |
| **400** | Bad request |  -  |
| **404** | Template not found |  -  |

## updateDynamicTemplateWithHttpInfo

> ApiResponse<DynamicTemplateResponse> updateDynamicTemplate updateDynamicTemplateWithHttpInfo(id, dataName, dataBody)

Update a dynamic template

Update an existing Handlebars template

### Example

```java
// Import classes:
import com.paubox.ApiClient;
import com.paubox.ApiException;
import com.paubox.ApiResponse;
import com.paubox.Configuration;
import com.paubox.auth.*;
import com.paubox.models.*;
import com.paubox.api.DynamicTemplatesApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.paubox.net/v1/YOUR_API_USERNAME");
        
        // Configure API key authorization: PauboxToken
        ApiKeyAuth PauboxToken = (ApiKeyAuth) defaultClient.getAuthentication("PauboxToken");
        PauboxToken.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //PauboxToken.setApiKeyPrefix("Token");

        DynamicTemplatesApi apiInstance = new DynamicTemplatesApi(defaultClient);
        String id = "id_example"; // String | Template ID to update
        String dataName = "dataName_example"; // String | Updated name for the template
        File dataBody = new File("/path/to/file"); // File | Updated Handlebars template file (.hbs)
        try {
            ApiResponse<DynamicTemplateResponse> response = apiInstance.updateDynamicTemplateWithHttpInfo(id, dataName, dataBody);
            System.out.println("Status code: " + response.getStatusCode());
            System.out.println("Response headers: " + response.getHeaders());
            System.out.println("Response body: " + response.getData());
        } catch (ApiException e) {
            System.err.println("Exception when calling DynamicTemplatesApi#updateDynamicTemplate");
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
| **id** | **String**| Template ID to update | |
| **dataName** | **String**| Updated name for the template | [optional] |
| **dataBody** | **File**| Updated Handlebars template file (.hbs) | [optional] |

### Return type

ApiResponse<[**DynamicTemplateResponse**](DynamicTemplateResponse.md)>


### Authorization

[PauboxToken](../README.md#PauboxToken)

### HTTP request headers

- **Content-Type**: multipart/form-data
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Template updated successfully |  -  |
| **400** | Bad request |  -  |
| **404** | Template not found |  -  |

