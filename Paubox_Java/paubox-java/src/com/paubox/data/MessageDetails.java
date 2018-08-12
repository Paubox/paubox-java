/**
 * 
 */
package com.paubox.data;

import java.util.List;

/**
 * @author Vaibhavi
 *
 */
public class MessageDetails {

	 private String id;
     private List<MessageDeliveries> message_deliveries;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the message_deliveries
	 */
	public List<MessageDeliveries> getMessage_deliveries() {
		return message_deliveries;
	}
	/**
	 * @param message_deliveries the message_deliveries to set
	 */
	public void setMessage_deliveries(List<MessageDeliveries> message_deliveries) {
		this.message_deliveries = message_deliveries;
	}
	
     
}
