package com.paubox.service;

import com.paubox.data.GetEmailDispositionResponse;
import com.paubox.data.Message;
import com.paubox.data.SendMessageResponse;

public interface EmailInterface {
	
	public  GetEmailDispositionResponse GetEmailDisposition(String sourceTrackingId) throws Exception;
	
	public  SendMessageResponse SendMessage(Message message) throws Exception;

}
