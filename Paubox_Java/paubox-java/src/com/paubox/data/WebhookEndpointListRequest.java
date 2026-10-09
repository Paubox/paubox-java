package com.paubox.data;

/**
 * Optional pagination for listing endpoints. A null field is left off the
 * query string, letting the service pick its own default.
 */
public class WebhookEndpointListRequest {

	private Integer page;
	private Integer items;

	public Integer getPage() { return page; }
	public void setPage(Integer page) { this.page = page; }

	public Integer getItems() { return items; }
	public void setItems(Integer items) { this.items = items; }
}
