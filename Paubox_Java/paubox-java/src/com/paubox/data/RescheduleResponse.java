package com.paubox.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RescheduleResponse {

	private String sourceTrackingId;
	private String scheduledAt;
	private String data;

	public String getSourceTrackingId() {
		return sourceTrackingId;
	}

	public void setSourceTrackingId(String sourceTrackingId) {
		this.sourceTrackingId = sourceTrackingId;
	}

	public String getScheduledAt() {
		return scheduledAt;
	}

	public void setScheduledAt(String scheduledAt) {
		this.scheduledAt = scheduledAt;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
