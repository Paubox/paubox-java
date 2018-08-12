/**
 * 
 */
package com.paubox.data;

/**
 * @author Vaibhavi
 *
 */
public class MessageDeliveries {

	private String recipient ;
    private MessageStatus status ;
	/**
	 * @return the recipient
	 */
	public String getRecipient() {
		return recipient;
	}
	/**
	 * @param recipient the recipient to set
	 */
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	/**
	 * @return the status
	 */
	public MessageStatus getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(MessageStatus status) {
		this.status = status;
	}
    
    
    
    
}
