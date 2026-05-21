# Paubox Java SDK — API Reference

## Email API

Base URL: `https://api.paubox.net/v1/{API_USER}/`  
Authentication: `Authorization: Token token={API_KEY}`

### Setup

```java
ConfigurationManager.getProperties("/path/to/config.properties");
EmailInterface email = new EmailService();
```

`config.properties` must contain:
```
APIKEY=your-api-key
APIUSER=your-api-username
```

---

### sendMessage

Send a HIPAA-compliant email.

```java
SendMessageResponse response = email.sendMessage(message);
```

**Message fields**

| Field | Type | Required | Description |
|---|---|---|---|
| `recipients` | `String[]` | Yes | To addresses |
| `header` | `Header` | Yes | Subject, from, reply-to |
| `content` | `Content` | Yes | Plain text and/or HTML body |
| `cc` | `String[]` | No | CC addresses |
| `bcc` | `String[]` | No | BCC addresses |
| `attachments` | `List<Attachment>` | No | File attachments |
| `allowNonTLS` | `boolean` | No | Allow delivery over non-TLS (default false) |
| `forceSecureNotification` | `String` | No | `"true"` / `"false"` |

**Header fields**

| Field | Type | Required |
|---|---|---|
| `from` | `String` | Yes |
| `subject` | `String` | Yes |
| `replyTo` | `String` | No |

**Attachment fields**

| Field | Type | Description |
|---|---|---|
| `fileName` | `String` | Filename including extension |
| `contentType` | `String` | MIME type (e.g. `application/pdf`) |
| `content` | `String` | Base64-encoded file bytes |

**SendMessageResponse fields**

| Field | Type | Description |
|---|---|---|
| `sourceTrackingId` | `String` | ID for tracking delivery (non-null on success) |
| `data` | `String` | Response data (non-null on success) |
| `errors` | `List<Error>` | Non-null on failure |

---

### getEmailDisposition

Check delivery and open status for a previously sent message.

```java
GetEmailDispositionResponse response = email.getEmailDisposition(sourceTrackingId);
```

**Parameters**

| Parameter | Type | Description |
|---|---|---|
| `sourceTrackingId` | `String` | Tracking ID from `sendMessage` response |

**GetEmailDispositionResponse fields**

| Field | Type | Description |
|---|---|---|
| `sourceTrackingId` | `String` | Echo of the requested ID |
| `data` | `MessageData` | Delivery details (non-null on success) |
| `errors` | `List<Error>` | Non-null on failure |

`MessageData` → `MessageDetails` → `message_deliveries: List<MessageDeliveries>`

**MessageDeliveries fields**

| Field | Type | Description |
|---|---|---|
| `recipient` | `String` | Recipient email address |
| `status` | `MessageStatus` | Delivery and open status |

**MessageStatus fields**

| Field | Type | Description |
|---|---|---|
| `deliveryStatus` | `String` | e.g. `"delivered"` |
| `deliveryTime` | `Timestamp` | When delivered |
| `openedStatus` | `String` | `"opened"` or `"unopened"` |
| `openedTime` | `Timestamp` | When first opened (null if unopened) |

---

## Forms API

Base URL: `https://apx.paubox.com/forms`  
Authentication: **None required**

### Setup

```java
FormsInterface forms = new FormsService();
```

No credentials needed — these endpoints are public.

---

### getForm

Retrieve the full definition of a form (HTML, JSON schema, CSS).

```java
Form form = forms.getForm("550e8400-e29b-41d4-a716-446655440000");
```

**Parameters**

| Parameter | Type | Description |
|---|---|---|
| `formId` | `String` | UUID of the form |

**Form response fields**

| Field | Type | Description |
|---|---|---|
| `id` | `String` | Form UUID |
| `title` | `String` | Form title |
| `description` | `String` | Optional description |
| `formHtml` | `String` | Rendered HTML of the form |
| `formJson` | `Object` | JSON schema of the form fields |
| `formCss` | `String` | Custom CSS for the form |
| `vanityUrl` | `String` | Optional vanity URL slug |
| `version` | `int` | Schema version |
| `active` | `boolean` | Whether the form accepts submissions |
| `customerId` | `int` | Owning customer ID |
| `signable` | `boolean` | Whether the form has a signature field |
| `signatureConfirmationLabel` | `String` | Label for signature confirmation |
| `submissionCount` | `int` | Total submissions received |
| `type` | `String` | Optional form type |
| `deleted` | `boolean` | Whether the form is soft-deleted |
| `archived` | `boolean` | Whether the form is archived |
| `createdAt` | `String` | ISO 8601 creation timestamp |
| `updatedAt` | `String` | ISO 8601 last-updated timestamp |

Throws `IOException` if the form is not found (404).

---

### submitForm

Submit a respondent's answers for a form. On success the service stores the submission, increments the form's submission count, and emails recipients if configured.

```java
Map<String, Object> data = new HashMap<>();
data.put("first_name", "Jane");
data.put("last_name", "Smith");
data.put("email", "jane@example.com");

FormSubmissionRequest submission = new FormSubmissionRequest(data);
forms.submitForm("550e8400-e29b-41d4-a716-446655440000", submission);
```

**Parameters**

| Parameter | Type | Description |
|---|---|---|
| `formId` | `String` | UUID of the form being submitted |
| `submission` | `FormSubmissionRequest` | Submission payload |

**FormSubmissionRequest fields**

| Field | Type | Required | Description |
|---|---|---|---|
| `formData` | `Map<String, Object>` | Yes | Key-value pairs matching the form's field schema |
| `attachments` | `List<FormSubmissionAttachment>` | No | File attachments |

**FormSubmissionAttachment fields**

| Field | Type | Description |
|---|---|---|
| `name` | `String` | Filename (e.g. `consent.pdf`) |
| `content` | `String` | Base64-encoded file bytes |

Maximum request size is **250 MB** (supports file attachments).

**Example with attachment**

```java
FormSubmissionAttachment attachment = new FormSubmissionAttachment();
attachment.setName("consent.pdf");

byte[] fileBytes = Files.readAllBytes(Paths.get("consent.pdf"));
attachment.setContent(Base64.getEncoder().encodeToString(fileBytes));

List<FormSubmissionAttachment> attachments = new ArrayList<>();
attachments.add(attachment);
submission.setAttachments(attachments);

forms.submitForm(formId, submission);
```

Returns `void` on success (HTTP 201). Throws `Exception` on 400 (missing `form_data`) or 404 (form not found).
