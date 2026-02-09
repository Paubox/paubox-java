

# MessageHeaders


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**subject** | **String** |  |  |
|**from** | **String** | Must match the verified domain of your API key. |  |
|**replyTo** | **String** | Reply-to address; must match a verified domain if different from from. |  [optional] |
|**listUnsubscribe** | **String** | Insert a List-Unsubscribe header (mailto and/or http). See RFC guidance for syntax.  |  [optional] |
|**listUnsubscribePost** | **String** | Used in conjunction with List-Unsubscribe header. |  [optional] |
|**xCustomHeader** | **String** | Example custom header; any custom header may be added with an X- prefix. |  [optional] |
|**additionalProperties** | **String** | Any additional X- prefixed custom header values. |  [optional] |



