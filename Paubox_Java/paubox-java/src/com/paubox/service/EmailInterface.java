package com.paubox.service;

import com.paubox.data.GetEmailDispositionResponse;
import com.paubox.data.Message;
import com.paubox.data.SendMessageResponse;

public interface EmailInterface {
	
	/**
	 * 
	 * @param sourceTrackingId String
	 * @return GetEmailDispositionResponse
	 * @throws Exception
	 */
	public  GetEmailDispositionResponse getEmailDisposition(String sourceTrackingId) throws Exception;
	
	/**
	 * 
	 * @param message Message
	 * @return SendMessageResponse
	 * @throws Exception
	 */
	public  SendMessageResponse sendMessage(Message message) throws Exception;
	

}
