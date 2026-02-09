<img src="https://avatars.githubusercontent.com/u/22528478?s=200&v=4" alt="Paubox" width="150px">

# Paubox Java SDK

This is the official **Paubox Java SDK** for the [Paubox Email API](https://www.paubox.com/solutions/email-api).

The Paubox Email API allows your application to send secure, HIPAA compliant email via [Paubox](https://www.paubox.com) and track deliveries and opens.

# Table of Contents
* [Installation](#installation)
* [Usage](#usage)
* [Contributing](#contributing)
* [License](#license)

## External Resources
* [Documentation](https://docs.paubox.com/)
* [Quickstart Guide](https://docs.paubox.com/paubox_email_api/docs/quickstart/)

## Installation

### Getting Paubox API Credentials
You will need to have a Paubox account. You can [sign up here](https://www.paubox.com/pricing/paubox-email-api).

Once you have an account, follow the instructions on the REST API dashboard to verify domain ownership and generate API keys. Further [quickstart instructions for this process can be found here.](https://docs.paubox.com/paubox_email_api/docs/quickstart/)

### Install Package

### Maven

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.paubox</groupId>
    <artifactId>paubox-java</artifactId>
    <version>2.0.0</version>
</dependency>
```

### Manual JAR Installation

Download the JAR from the [`target/` directory](https://github.com/Paubox/paubox-java/tree/sdk-generation/v2.0.0-beta/target) and add it to your classpath:

```
java -cp paubox-java-2.0.0.jar:. YourApp
```

Or install to your local Maven repository:

```
mvn install:install-file -Dfile=paubox-java-2.0.0.jar \
    -DgroupId=com.paubox -DartifactId=paubox-java \
    -Dversion=2.0.0 -Dpackaging=jar
```

## Usage

Please refer to the [API Endpoints](#documentation-for-api-endpoints) section below for detailed usage of each endpoint.

For full API documentation and additional examples, visit [docs.paubox.com](https://docs.paubox.com/).

---

## Documentation for API Endpoints

All URIs are relative to *https://api.paubox.net/v1/YOUR_API_USERNAME*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*DynamicTemplatesApi* | [**createDynamicTemplate**](docs/DynamicTemplatesApi.md#createDynamicTemplate) | **POST** /dynamic_templates | Create a dynamic template
*DynamicTemplatesApi* | [**createDynamicTemplateWithHttpInfo**](docs/DynamicTemplatesApi.md#createDynamicTemplateWithHttpInfo) | **POST** /dynamic_templates | Create a dynamic template
*DynamicTemplatesApi* | [**deleteDynamicTemplate**](docs/DynamicTemplatesApi.md#deleteDynamicTemplate) | **DELETE** /dynamic_templates/{id} | Delete a dynamic template
*DynamicTemplatesApi* | [**deleteDynamicTemplateWithHttpInfo**](docs/DynamicTemplatesApi.md#deleteDynamicTemplateWithHttpInfo) | **DELETE** /dynamic_templates/{id} | Delete a dynamic template
*DynamicTemplatesApi* | [**getDynamicTemplate**](docs/DynamicTemplatesApi.md#getDynamicTemplate) | **GET** /dynamic_templates/{id} | Get a dynamic template
*DynamicTemplatesApi* | [**getDynamicTemplateWithHttpInfo**](docs/DynamicTemplatesApi.md#getDynamicTemplateWithHttpInfo) | **GET** /dynamic_templates/{id} | Get a dynamic template
*DynamicTemplatesApi* | [**listDynamicTemplates**](docs/DynamicTemplatesApi.md#listDynamicTemplates) | **GET** /dynamic_templates | List all dynamic templates
*DynamicTemplatesApi* | [**listDynamicTemplatesWithHttpInfo**](docs/DynamicTemplatesApi.md#listDynamicTemplatesWithHttpInfo) | **GET** /dynamic_templates | List all dynamic templates
*DynamicTemplatesApi* | [**sendTemplatedMessage**](docs/DynamicTemplatesApi.md#sendTemplatedMessage) | **POST** /templated_messages | Send a dynamically templated message
*DynamicTemplatesApi* | [**sendTemplatedMessageWithHttpInfo**](docs/DynamicTemplatesApi.md#sendTemplatedMessageWithHttpInfo) | **POST** /templated_messages | Send a dynamically templated message
*DynamicTemplatesApi* | [**updateDynamicTemplate**](docs/DynamicTemplatesApi.md#updateDynamicTemplate) | **PATCH** /dynamic_templates/{id} | Update a dynamic template
*DynamicTemplatesApi* | [**updateDynamicTemplateWithHttpInfo**](docs/DynamicTemplatesApi.md#updateDynamicTemplateWithHttpInfo) | **PATCH** /dynamic_templates/{id} | Update a dynamic template
*MessagesApi* | [**getMessageReceipt**](docs/MessagesApi.md#getMessageReceipt) | **GET** /message_receipt | Get email disposition
*MessagesApi* | [**getMessageReceiptWithHttpInfo**](docs/MessagesApi.md#getMessageReceiptWithHttpInfo) | **GET** /message_receipt | Get email disposition
*MessagesApi* | [**sendBulkMessages**](docs/MessagesApi.md#sendBulkMessages) | **POST** /bulk_messages | Send multiple email messages (batch)
*MessagesApi* | [**sendBulkMessagesWithHttpInfo**](docs/MessagesApi.md#sendBulkMessagesWithHttpInfo) | **POST** /bulk_messages | Send multiple email messages (batch)
*MessagesApi* | [**sendMessage**](docs/MessagesApi.md#sendMessage) | **POST** /messages | Send a single email message
*MessagesApi* | [**sendMessageWithHttpInfo**](docs/MessagesApi.md#sendMessageWithHttpInfo) | **POST** /messages | Send a single email message


## Documentation for Models

 - [Attachment](docs/Attachment.md)
 - [BulkSendRequest](docs/BulkSendRequest.md)
 - [BulkSendRequestData](docs/BulkSendRequestData.md)
 - [BulkSendResponse](docs/BulkSendResponse.md)
 - [BulkSendResponseMessagesInner](docs/BulkSendResponseMessagesInner.md)
 - [ClickData](docs/ClickData.md)
 - [DeleteDynamicTemplate200Response](docs/DeleteDynamicTemplate200Response.md)
 - [DeliveryStatus](docs/DeliveryStatus.md)
 - [DynamicTemplateListResponse](docs/DynamicTemplateListResponse.md)
 - [DynamicTemplateResponse](docs/DynamicTemplateResponse.md)
 - [ErrorResponse](docs/ErrorResponse.md)
 - [ErrorResponseErrorsInner](docs/ErrorResponseErrorsInner.md)
 - [Message](docs/Message.md)
 - [MessageContent](docs/MessageContent.md)
 - [MessageDelivery](docs/MessageDelivery.md)
 - [MessageHeaders](docs/MessageHeaders.md)
 - [MessageReceiptErrorResponse](docs/MessageReceiptErrorResponse.md)
 - [MessageReceiptResponse](docs/MessageReceiptResponse.md)
 - [MessageReceiptResponseData](docs/MessageReceiptResponseData.md)
 - [MessageReceiptResponseDataMessage](docs/MessageReceiptResponseDataMessage.md)
 - [SingleSendRequest](docs/SingleSendRequest.md)
 - [SingleSendRequestData](docs/SingleSendRequestData.md)
 - [SingleSendResponse](docs/SingleSendResponse.md)
 - [TemplatedMessage](docs/TemplatedMessage.md)
 - [TemplatedMessageHeaders](docs/TemplatedMessageHeaders.md)
 - [TemplatedMessageRequest](docs/TemplatedMessageRequest.md)
 - [TemplatedMessageRequestData](docs/TemplatedMessageRequestData.md)


<a id="documentation-for-authorization"></a>
## Documentation for Authorization


Authentication schemes defined for the API:
<a id="PauboxToken"></a>
### PauboxToken


- **Type**: API key
- **API key parameter name**: Authorization
- **Location**: HTTP header


## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.
However, the instances of the api clients created from the `ApiClient` are thread-safe and can be re-used.


## Contributing

The Paubox Java SDK is maintained by [Paubox, Inc.](https://www.paubox.com)

We want to empower our users building applications with the Paubox Email API, and so we encourage you to file bug reports/create GitHub issues and pull requests. Chances are other developers using our Email API might be having similar ideas about new features or approaches to improving the SDK, so we encourage you to upvote or comment on existing issues or pull requests!

## License

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

## Copyright
Copyright &copy; 2026, Paubox, Inc.
