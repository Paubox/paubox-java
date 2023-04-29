<img src="https://avatars.githubusercontent.com/u/22528478?s=200&v=4" alt="Paubox" width="150px">

# Paubox Java

This is the official Java wrapper for the [Paubox Email API](https://www.paubox.com/solutions/email-api). 

The Paubox Email API allows your application to send secure, HIPAA compliant email via Paubox and track deliveries and opens.
The API wrapper allows you to construct and send messages.

# Table of Contents
* [Installation](#installation)
*  [Usage](#usage)
*  [Contributing](#contributing)
*  [License](#license)

<a name="#installation"></a>
## Installation

Add the jar file (Paubox.Email.API.jar) in the classpath of your Java project.

### Getting Paubox API Credentials

You will need to have a Paubox account. You can [sign up here](https://www.paubox.com/join/see-pricing?unit=messages).

Once you have an account, follow the instructions on the Rest API dashboard to verify domain ownership and generate API credentials.

### Configuring API Credentials

Include your API credentials in a configuration file.

Create a configuration properties file, and add the following 2 properties to this file:

```java
APIKEY: Your-API-Key-Here
APIUSER: Your-Username-Here
```

Give the path to this configuration file as input to ConfigurationManager.getProperties()
method. e.g.
```java
ConfigurationManager.getProperties("E:\\projects\\PauboxTest\\src\\resources\\config.properties");
```

<a name="#usage"></a>
## Usage

To send email, prepare a Message object and call the SendMessage method of
EmailService using EmailInterface:

### Sending messages

```java
static SendMessageResponse SendMessage()
{
 Message message = new Message();
 Content content = new Content();
 Header header = new Header();
 message.setRecipients(new String[] { "someone@domain.com",
 "someoneelse@domain.com" });
 header.setFrom("you@yourdomain.com");
 message.setCc(new String[] { "cc-recipient@domain.com" });
 message.setBcc(new String[] { "bcc-recipient@domain.com" });
 header.setSubject("Testing!");
 header.setReplyTo("reply-to@yourdomain.com");
 content.setPlainText("Hello World!");
 message.setHeader(header);
 message.setContent(content);

 EmailInterface email = new EmailService();
 SendMessageResponse response = email.SendMessage(message);
 return response;
}
```

### Allowing non-TLS message delivery

If you want to send non-PHI mail that does not need to be HIPAA-compliant, you can allow the message delivery to take place even if a TLS connection is unavailable.

This means the message will not be converted into a secure portal message when a nonTLS connection is encountered. To do this, just pass true to message.setAllowNonTLS() method, as shown below:

```java
static SendMessageResponse SendNonTLSMessage()
{
 Message message = new Message();
 Content content = new Content();
 Header header = new Header();
 message.setRecipients(new String[] { "someone@domain.com",
 “someoneelse@domain.com“ });
 header.setFrom("you@yourdomain.com");
 message.setBcc(new String[] { "bcc-recipient@domain.com" });
 header.setSubject("Testing!");
 header.setReplyTo("reply-to@yourdomain.com");
 content.setPlainText("Hello World!");
 message.setAllowNonTLS(true);
 message.setHeader(header);
 message.setContent(content);

 EmailInterface email = new EmailService();
 SendMessageResponse response = email.SendMessage(message);
 return response;
}
```

### Forcing Secure Notifications

Paubox Secure Notifications allow an extra layer of security, especially when coupled with an organization's requirement for message recipients to use 2-factor authentication to read messages (this setting is available to org administrators in the Paubox Admin Panel).

Instead of receiving an email with the message contents, the recipient will receive a notification email that they have a new message in Paubox.

```java
static SendMessageResponse SendForceSecureNotificationMessage()
{
 Message message = new Message();
 Content content = new Content();
 Header header = new Header();
 message.setRecipients(new String[] { "someone@domain.com",
 “someoneelse@domain.com“ });
 header.setFrom("you@yourdomain.com");
 message.setBcc(new String[] { "bcc-recipient@domain.com" });
 header.setSubject("Testing!");
 header.setReplyTo("reply-to@yourdomain.com");
 content.setPlainText("Hello World!");
 message.setForceSecureNotification("true");
 message.setHeader(header);
 message.setContent(content);

 EmailInterface email = new EmailService();
 SendMessageResponse response = email.SendMessage(message);
 return response;
}
```

### Adding Attachments

#### Plain text Attachments

Below is some sample code to send a plain text attachment.

```java
static SendMessageResponse SendMessage()
{
 Message message = new Message();
 Content content = new Content();
 Header header = new Header();
 message.setRecipients(new String[] { "someone@domain.com",
 “someoneelse@domain.com“ });
 header.setFrom("you@yourdomain.com");
 message.setBcc(new String[] { "bcc-recipient@domain.com" });
 header.setSubject("Testing!");
 header.setReplyTo("reply-to@yourdomain.com");
 content.setPlainText("Hello World!");
 message.setHeader(header);
 message.setContent(content);

 // Base64 encode attachment contents and use a valid content type.
 Attachment attachment = new Attachment();
 List<Attachment> listAttachments = new ArrayList<Attachment>();
 attachment.setFileName("hello_world.txt");
 attachment.setContentType("text/plain");
 attachment.setContent("SGVsbG8gV29ybGQh\n");
 listAttachments.add(attachment);

 EmailInterface email = new EmailService();
 SendMessageResponse response = email.SendMessage(message);
 return response;
}
```

#### PDF Attachments
Provided below is some sample code to send an email with a PDF attachment, with slight modification this code will also work for any other type of file attachment which contains binary data.

Two things to be noted here are ...
- The contentType specified needs to match the file type, please refer to this link for a list of [content type](https://cloud.google.com/appengine/docs/standard/php/mail/mail-with-headers-attachments) values.
- The attachment content needs to be read from the file in bytes and then converted to a base64 encoded string before calling Attachment.setContent(), the conversion to base64 is the responsibility of the user.

```java
static SendMessageResponse SendMessage()
{
 Message message = new Message();
 Content content = new Content();
 Header header = new Header();
 message.setRecipients(new String[] { "someone@domain.com",
 “someoneelse@domain.com“ });
 header.setFrom("you@yourdomain.com");
 message.setBcc(new String[] { "bcc-recipient@domain.com" });
 header.setSubject("Testing!");
 header.setReplyTo("reply-to@yourdomain.com");
 content.setPlainText("Hello World!");
 message.setHeader(header);
 message.setContent(content);

 // Base64 encode attachment contents and use a valid content type.
 Attachment attachment = new Attachment();
 List<Attachment> listAttachments = new ArrayList<Attachment>();

 attachment.setFileName("testFile.pdf");
 attachment.setContentType("application/pdf");

 byte[] input_file = Files.readAllBytes(Paths.get("testFile.pdf"));
 byte[] encodedBytes = Base64.getEncoder().encode(input_file);

 String pdfInBase64 = new String(encodedBytes);

 attachment.setContent(pdfInBase64);
 
 listAttachments.add(attachment);

 EmailInterface email = new EmailService();
 SendMessageResponse response = email.SendMessage(message);
 return response;
}
```


### Checking Email Dispositions

To check the status for any email, use its source tracking id and call the GetEmailDisposition method of EmailService using EmailInterface:

```java
static void GetEmailDisposition()
{
 EmailInterface email = new EmailService();
 GetEmailDispositionResponse response = email.GetEmailDisposition(“2a3c048485aa4cf6”);
}
```

## Dynamic Template

- Create a dynamic template
- Update a dynamic template
- Delete a dynamic template
- Get a list of dynamic templates
- Get a specific dynamic template


### Usage
1. Import the DynamicTemplateService class in your Java code:

```Java
import com.paubox.service.DynamicTemplateService;
```

2. Create an instance of the DynamicTemplateService class with your credentials:

```Java
DynamicTemplateService service = new DynamicTemplateService(USERNAME, API_KEY);
```
4. Call the API methods as needed:
```Java
// Create a dynamic template
String createTemplateResponse = service.createDynamicTemplate(TEMPLATE_NAME, TEMPLATE_FILE);

// Update a dynamic template
String updateTemplateResponse = service.updateDynamicTemplate(TEMPLATE_ID, TEMPLATE_NAME, TEMPLATE_FILE);

// Delete a dynamic template
String deleteTemplateResponse = service.deleteDynamicTemplate(TEMPLATE_ID);

// Get a list of dynamic templates
List<DynamicTemplate> templates = service.getAllDynamicTemplates();

// Get a specific dynamic template
DynamicTemplate template = service.getDynamicTemplate(TEMPLATE_ID);
```

5. Check the responses returned by the API calls:
```Java
System.out.println(createTemplateResponse);
System.out.println(updateTemplateResponse);
System.out.println(deleteTemplateResponse);
System.out.println(templates);
System.out.println(template);
```

## Webhook Endpoint

- Create a webhook endpoint
- Update a webhook endpoint
- Delete a webhook endpoint
- Get a list of webhook endpoints
- Get a specific webhook endpoint


### Usage
1. Import the WebhookEndpointService class in your Java code:

```Java
import com.paubox.service.WebhookEndpointService;
```

2. Create an instance of the WebhookEndpointService class:

```Java
DynamicTemplateService service = new WebhookEndpointService();
```
4. Call the API methods as needed:
```Java
// Create a dynamic template
WebhookEndpointResponse CreateResponse = service.createWebhookEndpoint(WebhookEndpointRequest request);

// Update a dynamic template
WebhookEndpointResponse UpdateResponse = service.updateWebhookEndpoint(String endpointId, WebhookEndpointRequest request);

// Delete a dynamic template
WebhookEndpointResponse DeleteResponse = deleteWebhookEndpoint(String templateId);

// Get a list of dynamic templates
List<WebhookEndpoint> webhooks = service.getAllWebhookEndpoints();

// Get a specific dynamic template
WebhookEndpoint webhook = service.getWebhookEndpoint(String templateId);
```


<a name="#contributing"></a>
## Contributing

Bug reports and pull requests are welcome on GitHub at https://github.com/paubox/paubox-java.


<a name="#license"></a>
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
Copyright &copy; 2021, Paubox, Inc.
