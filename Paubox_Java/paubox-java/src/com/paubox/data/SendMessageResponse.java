package com.paubox.data;

import java.util.List;

public class SendMessageResponse {

	private String sourceTrackingId;
    private String data;
    private List<Error> errors;
	/**
	 * @return the sourceTrackingId
	 */
	public String getSourceTrackingId() {
		return sourceTrackingId;
	}
	/**
	 * @param sourceTrackingId the sourceTrackingId to set
	 */
	public void setSourceTrackingId(String sourceTrackingId) {
		this.sourceTrackingId = sourceTrackingId;
	}
	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	/**
	 * @return the errors
	 */
	public List<Error> getErrors() {
		return errors;
	}
	/**
	 * @param errors the errors to set
	 */
	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SendMessageResponse [sourceTrackingId=" + sourceTrackingId + ", data=" + data + ", errors=" + errors
				+ "]";
	}
    
    
}
