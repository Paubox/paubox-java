<img src="https://avatars.githubusercontent.com/u/22528478?s=200&v=4" alt="Paubox" width="150px">

# Paubox Java

**NEW:** [Version 2 of the Paubox Email API SDK for Java](https://github.com/Paubox/paubox-java/tree/sdk-generation/v2.0.0-beta) is available to beta test now. It includes code for newer features like bulk message sending, dynamic templates, and more. We will be deprecating the old in the near future.

This is the official Java wrapper for the [Paubox Email API](https://www.paubox.com/products/paubox-email-api). 

The Paubox Email API allows your application to send secure, HIPAA compliant email via Paubox and track deliveries and opens.
The API wrapper allows you to construct and send messages.

# Table of Contents
* [Installation](#installation)
* [Usage](#usage)
  * [Sending messages](#sending-messages)
  * [Checking Email Dispositions](#checking-email-dispositions)
  * [Paubox Forms](#paubox-forms)
    * [Respondent endpoints](#forms-respondent-endpoints)
    * [Forms management](#forms-management)
* [Contributing](#contributing)
* [License](#license)

<a name="#installation"></a>
## Installation

Add the jar file (Paubox.Email.API.jar) in the classpath of your Java project.

### Getting Paubox API Credentials

You will need to have a Paubox account. You can [sign up here](https://www.paubox.com/pricing/paubox-email-api).

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

<a name="paubox-forms"></a>
## Paubox Forms

Paubox Forms is a secure form product included with Paubox Email Suite. The Forms API has two kinds of endpoints:

- **Respondent endpoints** — getting a form's public definition and submitting a response require **no API key**. They are public and called on behalf of form respondents.
- **Forms management endpoints** — listing, creating, updating, archiving, and copying forms, plus stats and reading/exporting submissions, require a **scoped API key** with the `forms` scope, created in the Paubox admin dashboard.

Pass the scoped API key either explicitly:

```java
FormsInterface forms = new FormsService("Your-Scoped-API-Key-Here");
```

or via the `FORMSAPIKEY` property in your configuration file, then use the no-arg constructor:

```java
FORMSAPIKEY: Your-Scoped-API-Key-Here
```

```java
ConfigurationManager.getProperties("/path/to/config.properties");
FormsInterface forms = new FormsService();
```

<a name="forms-respondent-endpoints"></a>
### Respondent endpoints

#### Getting a form definition

Retrieve a form's HTML, JSON schema, and CSS before rendering it:

```java
import com.paubox.data.Form;
import com.paubox.service.FormsInterface;
import com.paubox.service.FormsService;

static Form GetForm(String formId) throws Exception {
    FormsInterface forms = new FormsService();
    Form form = forms.getForm(formId);
    System.out.println("Title: " + form.getTitle());
    System.out.println("Active: " + form.isActive());
    return form;
}
```

#### Submitting a form response

Submit a respondent's answers. The `formData` keys must match the field names in the form's JSON schema (`form.getFormJson()`):

```java
import com.paubox.data.FormSubmissionRequest;
import com.paubox.service.FormsInterface;
import com.paubox.service.FormsService;
import java.util.HashMap;
import java.util.Map;

static void SubmitForm(String formId) throws Exception {
    Map<String, Object> data = new HashMap<>();
    data.put("first_name", "Jane");
    data.put("last_name", "Smith");
    data.put("email", "jane@example.com");

    FormSubmissionRequest submission = new FormSubmissionRequest(data);
    FormsInterface forms = new FormsService();
    forms.submitForm(formId, submission);
}
```

#### Submitting with file attachments

```java
import com.paubox.data.FormSubmissionAttachment;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

static void SubmitFormWithAttachment(String formId) throws Exception {
    Map<String, Object> data = new HashMap<>();
    data.put("first_name", "Jane");

    FormSubmissionAttachment attachment = new FormSubmissionAttachment();
    attachment.setName("consent.pdf");
    byte[] fileBytes = Files.readAllBytes(Paths.get("consent.pdf"));
    attachment.setContent(Base64.getEncoder().encodeToString(fileBytes));

    List<FormSubmissionAttachment> attachments = new ArrayList<>();
    attachments.add(attachment);

    FormSubmissionRequest submission = new FormSubmissionRequest(data);
    submission.setAttachments(attachments);

    FormsInterface forms = new FormsService();
    forms.submitForm(formId, submission);
}
```

Maximum submission size is **250 MB** to accommodate file attachments.

<a name="forms-management"></a>
### Forms management

All of the methods below require a **scoped API key** with the `forms` scope. Construct the service with `new FormsService(apiKey)`, or set the `FORMSAPIKEY` property and use `new FormsService()`.

#### Listing forms

List your forms with optional filtering and pagination. When authenticating with a scoped API key, `customerId` must be set to your own Paubox customer id — omitting it results in a `403 Forbidden` from the API. The other filters are optional (defaults: page 1, 50 items, newest first):

```java
import com.paubox.data.Form;
import com.paubox.data.FormListRequest;
import com.paubox.data.FormListResponse;
import com.paubox.service.FormsInterface;
import com.paubox.service.FormsService;

static void ListForms() throws Exception {
    FormListRequest query = new FormListRequest();
    query.setCustomerId(12345); // your Paubox customer id — required with a scoped API key
    query.setSearch("intake");
    query.setOrderBy("updated_at");
    query.setOrder("desc");
    query.setPage(1);
    query.setItems(25);

    FormsInterface forms = new FormsService();
    FormListResponse response = forms.listForms(query);

    for (Form form : response.getResults()) {
        System.out.println(form.getId() + " - " + form.getTitle());
    }
    System.out.println("Total forms: " + response.getPageInfo().getCount()
            + ", pages: " + response.getPageInfo().getPages());
}
```

#### Creating a form

`title`, `formJson`, `customerId`, and `version` are required; everything else is optional. Returns the new form's id:

```java
import com.paubox.data.CreateFormRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

static String CreateForm() throws Exception {
    Map<String, Object> formJson = new HashMap<>();
    formJson.put("body", new ArrayList<Object>());

    CreateFormRequest request = new CreateFormRequest("Patient Intake", formJson, 12345, 1);
    request.setDescription("New patient intake form");
    request.setRecipient("intake@yourdomain.com");
    request.setActive(true);

    FormsInterface forms = new FormsService();
    String newFormId = forms.createForm(request);
    return newFormId;
}
```

#### Getting a form by id (authenticated)

Unlike the public `getForm`, the authenticated `getFormById` returns the form even when it is inactive or archived:

```java
static Form GetFormById(String formId) throws Exception {
    FormsInterface forms = new FormsService();
    return forms.getFormById(formId);
}
```

#### Updating a form

Updates have PATCH semantics — any field you leave `null` on the request stays unchanged:

```java
import com.paubox.data.UpdateFormRequest;
import com.paubox.data.UpdateFormResponse;

static void UpdateForm(String formId) throws Exception {
    UpdateFormRequest request = new UpdateFormRequest();
    request.setTitle("Patient Intake (v2)");
    request.setActive(true);

    FormsInterface forms = new FormsService();
    UpdateFormResponse response = forms.updateForm(formId, request);
    System.out.println(response.getDetail());
}
```

#### Archiving and unarchiving a form

```java
static void ArchiveAndRestoreForm(String formId) throws Exception {
    FormsInterface forms = new FormsService();
    forms.archiveForm(formId);
    forms.unarchiveForm(formId);
}
```

#### Copying a form

Duplicate an existing form under a new title. Returns the full new Form:

```java
static Form CopyForm(String formId) throws Exception {
    FormsInterface forms = new FormsService();
    Form copy = forms.copyForm(formId, "Patient Intake (copy)");
    return copy;
}
```

#### Form stats

Get aggregate counts for your forms. Pass `null` to use the customer associated with your API key, or an explicit customer id:

```java
import com.paubox.data.FormStats;

static void GetFormStats() throws Exception {
    FormsInterface forms = new FormsService();
    FormStats stats = forms.getFormStats(null);
    System.out.println("Active forms: " + stats.getActiveFormCount());
    System.out.println("Total submissions: " + stats.getTotalSubmissionCount());
    System.out.println("Submissions in the last 7 days: " + stats.getSubmissionsLast7Days());
}
```

#### Listing form submissions

List a form's submissions with optional filtering and pagination. Pass `null` instead of a `FormSubmissionListRequest` to use all defaults:

```java
import com.paubox.data.FormSubmission;
import com.paubox.data.FormSubmissionListRequest;
import com.paubox.data.FormSubmissionListResponse;

static void ListFormSubmissions(String formId) throws Exception {
    FormSubmissionListRequest query = new FormSubmissionListRequest();
    query.setOrder("desc");
    query.setItems(20);

    FormsInterface forms = new FormsService();
    FormSubmissionListResponse response = forms.listFormSubmissions(formId, query);

    for (FormSubmission submission : response.getData()) {
        System.out.println(submission.getId() + " - " + submission.getSubmitterEmail());
    }
    System.out.println("Total submissions: " + response.getTotal());
}
```

#### Downloading submissions as CSV or PDF

Export all of a form's submissions (or a single submission) as CSV, or a single submission as PDF. The methods return the raw file bytes:

```java
import java.nio.file.Files;
import java.nio.file.Paths;

static void DownloadSubmissions(String formId, String submissionId) throws Exception {
    FormsInterface forms = new FormsService();

    // All submissions of a form, as CSV
    byte[] csv = forms.downloadSubmissionsCsv(formId);
    Files.write(Paths.get("submissions.csv"), csv);

    // A single submission, as CSV
    byte[] oneCsv = forms.downloadSubmissionCsv(formId, submissionId);
    Files.write(Paths.get("submission.csv"), oneCsv);

    // A single submission, as PDF
    byte[] pdf = forms.downloadSubmissionPdf(formId, submissionId);
    Files.write(Paths.get("submission.pdf"), pdf);
}
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
## 💬 Community & support

Questions, ideas, or want to share what you built? Join the **[Paubox Community](https://github.com/Paubox/community/discussions)** — the single home for discussions across every Paubox SDK and API.

🔐 Found a security issue? Email **devops@paubox.com** — please don't post it publicly.
