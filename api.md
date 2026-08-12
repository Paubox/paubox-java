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
Authentication: **None** for respondent endpoints (`getForm`, `submitForm`); **scoped API key** for all other methods

#### Authentication

Respondent endpoints (`getForm`, `submitForm`) are public — no credentials needed.

All other Forms methods (management endpoints) require a **scoped API key** with the `forms` scope, created in the Paubox admin dashboard. The SDK sends it as `Authorization: Bearer {FORMS_API_KEY}`. Calling a management method without a key throws `Exception`.

### Setup

Respondent endpoints (public):

```java
FormsInterface forms = new FormsService();
```

Management endpoints — pass the scoped API key explicitly:

```java
FormsInterface forms = new FormsService("your-scoped-api-key");
```

...or set `FORMSAPIKEY` in `config.properties` and use the no-arg constructor:

```java
ConfigurationManager.getProperties("/path/to/config.properties");
FormsInterface forms = new FormsService();
```

`config.properties` (only needed for management endpoints):
```
FORMSAPIKEY=your-scoped-api-key
```

---

### getForm

Retrieve the full definition of a form (HTML, JSON schema, CSS). Public — no API key. Only returns active, non-archived forms; use [`getFormById`](#getformbyid) to fetch a form regardless of state.

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
| `recipient` | `String` | Comma-separated emails notified on submission |
| `signable` | `boolean` | Whether the form has a signature field |
| `signatureConfirmationLabel` | `String` | Label for signature confirmation |
| `submissionCount` | `int` | Total submissions received |
| `type` | `String` | Optional form type (e.g. `marketing_form`) |
| `subscriptionListId` | `String` | Optional marketing subscription list ID |
| `oldFormId` | `Integer` | Legacy form ID, if migrated |
| `deleted` | `boolean` | Whether the form is soft-deleted |
| `archived` | `boolean` | Whether the form is archived |
| `createdAt` | `String` | ISO 8601 creation timestamp |
| `updatedAt` | `String` | ISO 8601 last-updated timestamp |

Throws `IOException` if the form is not found (404).

---

### submitForm

Submit a respondent's answers for a form. Public — no API key. On success the service stores the submission, increments the form's submission count, and emails recipients if configured.

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

---

### listForms

Requires a scoped API key. List forms with optional filtering and pagination.

```java
FormListResponse response = forms.listForms(query);
```

**Parameters**

| Parameter | Type | Description |
|---|---|---|
| `query` | `FormListRequest` | Filters/pagination; `customerId` is required when authenticating with a scoped API key |

**FormListRequest fields** (sent as query parameters, not JSON)

| Field | Type | Description |
|---|---|---|
| `customerId` | `Integer` | Customer ID to list forms for. **Required with a scoped API key** — must equal the key's customer id, otherwise the API returns `403 Forbidden` |
| `formId` | `String` | Filter to a single form UUID |
| `search` | `String` | Substring match against title/description |
| `order` | `String` | `asc` or `desc` (default `desc`) |
| `orderBy` | `String` | `title`, `updated_at`, or `submission_count` (default `created_at`) |
| `archived` | `Boolean` | Filter by archived state |
| `active` | `Boolean` | Filter by active state |
| `page` | `Integer` | Page number (default 1) |
| `items` | `Integer` | Items per page (default 50, max 100) |

**FormListResponse fields**

| Field | Type | Description |
|---|---|---|
| `results` | `List<Form>` | The forms on this page |
| `pageInfo` | `PageInfo` | Pagination metadata |

**PageInfo fields**

| Field | Type | Description |
|---|---|---|
| `count` | `long` | Total matching forms |
| `pages` | `int` | Total pages |
| `page` | `int` | Current page |
| `items` | `int` | Items per page |

---

### createForm

Requires a scoped API key. Create a new form. Returns the new form's UUID.

```java
String newFormId = forms.createForm(request);
```

**Parameters**

| Parameter | Type | Description |
|---|---|---|
| `request` | `CreateFormRequest` | The form to create |

**CreateFormRequest fields**

| Field | Type | Required | Description |
|---|---|---|---|
| `title` | `String` | Yes | Form title |
| `formJson` | `Object` | Yes | JSON schema of the form fields |
| `customerId` | `Integer` | Yes | Owning customer ID |
| `version` | `Integer` | Yes | Schema version |
| `description` | `String` | No | Description |
| `formHtml` | `String` | No | Rendered HTML of the form |
| `formCss` | `String` | No | Custom CSS |
| `recipient` | `String` | No | Comma-separated emails notified on submission |
| `signable` | `Boolean` | No | Whether the form has a signature field |
| `signatureConfirmationLabel` | `String` | No | Label for signature confirmation |
| `subscriptionListId` | `String` | No | Marketing subscription list ID |
| `type` | `String` | No | Form type (e.g. `marketing_form`) |
| `active` | `Boolean` | No | Whether the form accepts submissions |
| `submissionCount` | `Integer` | No | Initial submission count |

The convenience constructor `CreateFormRequest(String title, Object formJson, Integer customerId, Integer version)` sets the four required fields.

Throws `Exception` if `request`, `title`, `formJson`, `customerId`, or `version` is missing.

---

<a name="getformbyid"></a>
### getFormById

Requires a scoped API key. Retrieve a form by ID regardless of its active/archived state (unlike the public `getForm`).

```java
Form form = forms.getFormById(formId);
```

**Parameters**

| Parameter | Type | Description |
|---|---|---|
| `formId` | `String` | UUID of the form |

Returns a `Form` (same fields as [`getForm`](#getform)). Throws `IOException` if the form is not found (404).

---

### updateForm

Requires a scoped API key. Update a form. PATCH semantics: fields left `null` on the request stay unchanged.

```java
UpdateFormResponse response = forms.updateForm(formId, request);
```

**Parameters**

| Parameter | Type | Description |
|---|---|---|
| `formId` | `String` | UUID of the form to update |
| `request` | `UpdateFormRequest` | Fields to change (null = leave unchanged) |

**UpdateFormRequest fields** (all optional)

| Field | Type | Description |
|---|---|---|
| `title` | `String` | New title |
| `description` | `String` | New description |
| `formJson` | `Object` | New JSON schema |
| `vanityUrl` | `String` | New vanity URL slug |
| `recipient` | `String` | Comma-separated notification emails |
| `active` | `Boolean` | Enable/disable submissions |
| `subscriptionListId` | `String` | Marketing subscription list ID |

**UpdateFormResponse fields**

| Field | Type | Description |
|---|---|---|
| `detail` | `String` | e.g. `"Form updated successfully"` |
| `formId` | `String` | UUID of the updated form |

Throws `IOException` if the form is not found (404).

---

### archiveForm / unarchiveForm

Requires a scoped API key. Archive or unarchive a form. No request body; returns `void`.

```java
forms.archiveForm(formId);
forms.unarchiveForm(formId);
```

**Parameters**

| Parameter | Type | Description |
|---|---|---|
| `formId` | `String` | UUID of the form |

Throws `IOException` on error (e.g. form not found).

---

### copyForm

Requires a scoped API key. Duplicate an existing form under a new title. Returns the full new `Form`.

```java
Form copy = forms.copyForm(formId, "New title");
```

**Parameters**

| Parameter | Type | Description |
|---|---|---|
| `formId` | `String` | UUID of the source form |
| `newTitle` | `String` | Title for the copy |

Throws `IOException` if the source form is not found (404).

---

### getFormStats

Requires a scoped API key. Get aggregate form/submission counts.

```java
FormStats stats = forms.getFormStats(null);
```

**Parameters**

| Parameter | Type | Description |
|---|---|---|
| `customerId` | `Integer` | Optional customer ID; `null` uses the API key's customer |

**FormStats fields**

| Field | Type | Description |
|---|---|---|
| `activeFormCount` | `long` | Number of active forms |
| `totalSubmissionCount` | `long` | Total submissions across forms |
| `submissionsLast7Days` | `long` | Submissions in the last 7 days |

---

### listFormSubmissions

Requires a scoped API key. List a form's submissions with optional filtering and pagination.

```java
FormSubmissionListResponse response = forms.listFormSubmissions(formId, query);
```

**Parameters**

| Parameter | Type | Description |
|---|---|---|
| `formId` | `String` | UUID of the form |
| `query` | `FormSubmissionListRequest` | Optional filters/pagination; pass `null` for all defaults |

**FormSubmissionListRequest fields** (all optional; sent as query parameters, not JSON)

| Field | Type | Description |
|---|---|---|
| `submissionId` | `String` | Filter to a single submission |
| `order` | `String` | `asc` or `desc` |
| `orderBy` | `String` | `submitter_email` (default `created_at`) |
| `page` | `Integer` | Page number (default 1) |
| `items` | `Integer` | Items per page (default 50, max 100) |

**FormSubmissionListResponse fields**

| Field | Type | Description |
|---|---|---|
| `data` | `List<FormSubmission>` | The submissions on this page |
| `total` | `long` | Total matching submissions |
| `page` | `int` | Current page |
| `items` | `int` | Items per page |

**FormSubmission fields**

| Field | Type | Description |
|---|---|---|
| `id` | `String` | Submission UUID |
| `formId` | `String` | UUID of the form |
| `formData` | `String` | The submission's answers as a JSON string |
| `storageType` | `String` | Where the submission is stored |
| `storageUrl` | `String` | Storage URL (nullable) |
| `submitterEmail` | `String` | Respondent's email (nullable) |
| `recipients` | `String` | Notified recipients (nullable) |
| `attachment` | `String` | Attachment reference (nullable) |
| `attachmentName` | `String` | Attachment filename (nullable) |
| `attachmentUrl` | `String` | Attachment URL (nullable) |
| `attachmentType` | `String` | Attachment MIME type (nullable) |
| `createdAt` | `String` | ISO 8601 creation timestamp |

Throws `IOException` if the form is not found (404).

---

### downloadSubmissionsCsv

Requires a scoped API key. Download all of a form's submissions as CSV. Returns the raw `text/csv` bytes.

```java
byte[] csv = forms.downloadSubmissionsCsv(formId);
```

**Parameters**

| Parameter | Type | Description |
|---|---|---|
| `formId` | `String` | UUID of the form |

Throws `Exception` on a non-200 response.

---

### downloadSubmissionCsv

Requires a scoped API key. Download a single submission as CSV. Returns the raw `text/csv` bytes.

```java
byte[] csv = forms.downloadSubmissionCsv(formId, submissionId);
```

**Parameters**

| Parameter | Type | Description |
|---|---|---|
| `formId` | `String` | UUID of the form |
| `submissionId` | `String` | UUID of the submission |

Throws `Exception` on a non-200 response.

---

### downloadSubmissionPdf

Requires a scoped API key. Download a single submission as PDF. Returns the raw `application/pdf` bytes.

```java
byte[] pdf = forms.downloadSubmissionPdf(formId, submissionId);
```

**Parameters**

| Parameter | Type | Description |
|---|---|---|
| `formId` | `String` | UUID of the form |
| `submissionId` | `String` | UUID of the submission |

Throws `Exception` on a non-200 response.
