

# MessageReceiptResponseDataMessage


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | The message ID |  |
|**messageDeliveries** | [**List&lt;MessageDelivery&gt;**](MessageDelivery.md) |  |  |
|**totalOpens** | **Integer** | Total number of opens (single recipient only) |  [optional] |
|**distinctOpens** | **Integer** | Number of distinct opens (single recipient only) |  [optional] |
|**totalClickCount** | **Integer** | Total number of clicks (single recipient only) |  [optional] |
|**clicksPerLink** | [**List&lt;ClickData&gt;**](ClickData.md) | Click tracking data per link (single recipient only) |  [optional] |
|**unsubscribed** | **Boolean** | Whether the recipient has unsubscribed (single recipient only) |  [optional] |



