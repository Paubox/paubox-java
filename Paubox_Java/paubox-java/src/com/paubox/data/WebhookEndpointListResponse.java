package com.paubox.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A page of endpoints together with its pagination metadata.
 *
 * <p>The envelope is kept rather than flattened to the rows: {@code pageInfo}
 * is what a caller paginating needs, and {@code count} is the total matching
 * rather than the length of {@code data}.</p>
 */
public class WebhookEndpointListResponse {

	private List<WebhookEndpoint> data;

	@JsonProperty("page_info")
	private WebhookPageInfo pageInfo;

	public List<WebhookEndpoint> getData() { return data; }
	public void setData(List<WebhookEndpoint> data) { this.data = data; }

	public WebhookPageInfo getPageInfo() { return pageInfo; }
	public void setPageInfo(WebhookPageInfo pageInfo) { this.pageInfo = pageInfo; }
}
