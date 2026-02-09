

# DeliveryStatus


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**deliveryStatus** | [**DeliveryStatusEnum**](#DeliveryStatusEnum) | The delivery status of the message |  |
|**deliveryTime** | **String** | The time when the message was delivered (if applicable) |  [optional] |
|**openedStatus** | [**OpenedStatusEnum**](#OpenedStatusEnum) | Whether the message was opened (single recipient only) |  [optional] |
|**openedTime** | **String** | The time when the message was first opened (single recipient only) |  [optional] |



## Enum: DeliveryStatusEnum

| Name | Value |
|---- | -----|
| PROCESSING | &quot;processing&quot; |
| TLS_NOT_OFFERED_SENDING_VIA_SECURE_PORTAL | &quot;TLS not offered, sending via Secure Portal&quot; |
| SOFT_BOUNCED | &quot;soft bounced&quot; |
| SOFT_BOUNCED_MAILBOX_FULL | &quot;soft bounced - mailbox full&quot; |
| HARD_BOUNCED | &quot;hard bounced&quot; |
| INTERNAL_ERROR_PLEASE_CHECK_BACK_LATER_ | &quot;Internal error. Please check back later.&quot; |
| DELIVERED | &quot;delivered&quot; |
| DELIVERED_VIA_SECURE_PORTAL | &quot;delivered via secure portal&quot; |



## Enum: OpenedStatusEnum

| Name | Value |
|---- | -----|
| OPENED | &quot;opened&quot; |
| NOT_OPENED | &quot;not opened&quot; |



