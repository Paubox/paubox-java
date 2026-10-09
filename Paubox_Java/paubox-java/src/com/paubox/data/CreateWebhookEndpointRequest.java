package com.paubox.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Body for creating an endpoint.
 *
 * <p>Event names are not validated here on purpose: the catalog belongs to the
 * service and grows without an SDK release. An event the key is not scoped for
 * comes back 403, an unrecognised one 422.</p>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateWebhookEndpointRequest {

	@JsonProperty("target_url")
	private String targetUrl;

	private List<String> events;

	public CreateWebhookEndpointRequest() {
	}

	public CreateWebhookEndpointRequest(String targetUrl, List<String> events) {
		this.targetUrl = targetUrl;
		this.events = events;
	}

	public String getTargetUrl() { return targetUrl; }
	public void setTargetUrl(String targetUrl) { this.targetUrl = targetUrl; }

	public List<String> getEvents() { return events; }
	public void setEvents(List<String> events) { this.events = events; }
}
