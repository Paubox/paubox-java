package com.paubox.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A configured webhook endpoint.
 *
 * <p>Timestamps are RFC 3339 with microsecond precision and an explicit
 * {@code +00:00} offset. They are kept as strings so nothing is lost rounding
 * them through a date type the caller may not want.</p>
 */
public class WebhookEndpoint {

	private String id;

	@JsonProperty("target_url")
	private String targetUrl;

	/** "active" or "disabled". */
	private String status;

	private List<String> events;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("updated_at")
	private String updatedAt;

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	public String getTargetUrl() { return targetUrl; }
	public void setTargetUrl(String targetUrl) { this.targetUrl = targetUrl; }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }

	public List<String> getEvents() { return events; }
	public void setEvents(List<String> events) { this.events = events; }

	public String getCreatedAt() { return createdAt; }
	public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

	public String getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
