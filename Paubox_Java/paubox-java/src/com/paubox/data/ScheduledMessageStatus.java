package com.paubox.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduledMessageStatus {

	private String sourceTrackingId;
	private String scheduledAt;
	private String state;
	private long messageId;
	private String sentAt;
	private String cancelledAt;
	private String errorMessage;

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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public String getSentAt() {
		return sentAt;
	}

	public void setSentAt(String sentAt) {
		this.sentAt = sentAt;
	}

	public String getCancelledAt() {
		return cancelledAt;
	}

	public void setCancelledAt(String cancelledAt) {
		this.cancelledAt = cancelledAt;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
