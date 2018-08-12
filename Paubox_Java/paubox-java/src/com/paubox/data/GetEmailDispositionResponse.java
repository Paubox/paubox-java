package com.paubox.data;

import java.util.List;

public class GetEmailDispositionResponse {

	 private String sourceTrackingId ;
	 private MessageData data;
     private List<String> errors;
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
	 * @return the errors
	 */
	public List<String> getErrors() {
		return errors;
	}
	/**
	 * @param errors the errors to set
	 */
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GetEmailDispositionResponse [sourceTrackingId=" + sourceTrackingId + ", data=" + data + ", errors="
				+ errors + "]";
	}
	/**
	 * @return the data
	 */
	public MessageData getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(MessageData data) {
		this.data = data;
	} 
	
	
     
	
}
