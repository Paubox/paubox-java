

# TemplatedMessageHeaders


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**subject** | **String** | Message subject (can include template variables) |  |
|**from** | **String** | Must match the verified domain of your API key. |  |
|**replyTo** | **String** | Reply-to address; must match a verified domain if different from from. |  [optional] |
|**listUnsubscribe** | **String** | Insert a List-Unsubscribe header (mailto and/or http). See RFC guidance for syntax.  |  [optional] |
|**listUnsubscribePost** | **String** | Used in conjunction with List-Unsubscribe header. |  [optional] |
|**additionalProperties** | **String** | Any additional custom header values. |  [optional] |



