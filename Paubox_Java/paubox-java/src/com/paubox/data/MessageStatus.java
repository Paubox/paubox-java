/**
 * 
 */
package com.paubox.data;

import java.sql.Timestamp;

public class MessageStatus {
	
	 private String deliveryStatus;
	 private Timestamp deliveryTime;
	 private String openedStatus;        
	 private Timestamp openedTime;
	/**
	 * @return the deliveryStatus
	 */
	public String getDeliveryStatus() {
		return deliveryStatus;
	}
	/**
	 * @param deliveryStatus the deliveryStatus to set
	 */
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	/**
	 * @return the deliveryTime
	 */
	public Timestamp getDeliveryTime() {
		return deliveryTime;
	}
	/**
	 * @param deliveryTime the deliveryTime to set
	 */
	public void setDeliveryTime(Timestamp deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	/**
	 * @return the openedStatus
	 */
	public String getOpenedStatus() {
		return openedStatus;
	}
	/**
	 * @param openedStatus the openedStatus to set
	 */
	public void setOpenedStatus(String openedStatus) {
		this.openedStatus = openedStatus;
	}
	/**
	 * @return the openedTime
	 */
	public Timestamp getOpenedTime() {
		return openedTime;
	}
	/**
	 * @param openedTime the openedTime to set
	 */
	public void setOpenedTime(Timestamp openedTime) {
		this.openedTime = openedTime;
	}
	
     
     

}
