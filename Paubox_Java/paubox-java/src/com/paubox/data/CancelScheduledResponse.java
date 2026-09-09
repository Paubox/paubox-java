package com.paubox.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CancelScheduledResponse {

	private String sourceTrackingId;
	private String state;
	private String data;

	public String getSourceTrackingId() {
		return sourceTrackingId;
	}

	public void setSourceTrackingId(String sourceTrackingId) {
		this.sourceTrackingId = sourceTrackingId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
