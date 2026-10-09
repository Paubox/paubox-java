package com.paubox.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Fields to change on an existing endpoint.
 *
 * <p>Null fields are left out of the request rather than sent as null, so an
 * update that moves the target URL leaves the events and status alone.
 * Supplying {@code events} replaces the list rather than adding to it.</p>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateWebhookEndpointRequest {

	@JsonProperty("target_url")
	private String targetUrl;

	/** "active" or "disabled". */
	private String status;

	private List<String> events;

	public String getTargetUrl() { return targetUrl; }
	public void setTargetUrl(String targetUrl) { this.targetUrl = targetUrl; }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }

	public List<String> getEvents() { return events; }
	public void setEvents(List<String> events) { this.events = events; }
}
