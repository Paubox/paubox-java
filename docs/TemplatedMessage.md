

# TemplatedMessage


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**recipients** | **List&lt;String&gt;** |  |  |
|**bcc** | **List&lt;String&gt;** |  |  [optional] |
|**cc** | **List&lt;String&gt;** |  |  [optional] |
|**headers** | [**TemplatedMessageHeaders**](TemplatedMessageHeaders.md) |  |  |
|**allowNonTLS** | **Boolean** | Allow delivery over non-TLS rather than converting to a Secure Portal message. Not HIPAA-compliant if the message contains PHI.  |  [optional] |
|**forceSecureNotification** | **Boolean** | Force delivery as a Paubox Secure Message; recipient gets a pickup notification with a link.  |  [optional] |
|**attachments** | [**List&lt;Attachment&gt;**](Attachment.md) |  |  [optional] |



