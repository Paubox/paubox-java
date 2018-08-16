package com.paubox.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paubox.common.Constants;
import com.paubox.data.Attachment;
import com.paubox.data.GetEmailDispositionResponse;
import com.paubox.data.Message;
import com.paubox.data.MessageDeliveries;
import com.paubox.data.SendMessageResponse;

public class EmailService implements EmailInterface {

	private String baseApiUrl = "https://api.paubox.net/v1/" + Constants.API_USER + "/";

	public GetEmailDispositionResponse GetEmailDisposition(String sourceTrackingId) throws Exception {
		String url = baseApiUrl + "message_receipt?sourceTrackingId=" + sourceTrackingId ;
		String responseStr = APIHelper.callToAPIByGet(url, getAuthorizationHeader());
		ObjectMapper mapper = new ObjectMapper();
		GetEmailDispositionResponse response = (GetEmailDispositionResponse) mapper.readValue(responseStr,
				GetEmailDispositionResponse.class);
		 if (response.getData() == null && response.getSourceTrackingId() == null && response.getErrors() == null)
         {
             throw new Exception("Error while getting the response.");
         }

         if (response != null && response.getData() != null && response.getData().getMessage() != null
             && response.getData().getMessage().getMessage_deliveries() != null &&
             response.getData().getMessage().getMessage_deliveries().size() > 0)
         {
        	 for (MessageDeliveries messageDeliveries : response.getData().getMessage().getMessage_deliveries()) {
                 if (null!=messageDeliveries.getStatus().getOpenedStatus()) {
                	 messageDeliveries.getStatus().setOpenedStatus("unopened");
                 }
             }
         }
		return response;
	}

	public SendMessageResponse sendMessage(Message message) throws Exception {
		ObjectMapper mapper= new ObjectMapper();
		String reqBody=formatMessage(message);
		System.out.println(reqBody);
		String url=baseApiUrl+"messages";
		String responseStr = APIHelper.callToAPIByPost(url, getAuthorizationHeader(), reqBody);
		SendMessageResponse response = (SendMessageResponse) mapper.readValue(responseStr,
				SendMessageResponse.class);
		return response;
	}
	
	private String formatMessage(Message message) throws Exception{
		
		
		Message messageJSON= new Message();
		
		Map<String,String> hearderMap=new HashMap<String,String>();
		Map<String,String> contentMap=new HashMap<String,String>();
		
		if (message.getHeader() != null) {
			
			hearderMap.put("subject" , message.getHeader().getSubject());
			hearderMap.put("from" , message.getHeader().getFrom());
			hearderMap.put("reply-to" , message.getHeader().getReplyTo());
			messageJSON.setHearderMap(hearderMap);
			
        }
        else {
            throw new Exception("Message Header cannot be null.");
        }
		
		if (message.getContent() != null){
			
			contentMap.put("text/plain" , message.getContent().getPlainText());
			contentMap.put("text/html" , message.getContent().getHtmlText());
			messageJSON.setContentMap(contentMap);
			
        } else {
            throw new Exception("Message Content cannot be null.");
        }
		
		  //If there are attachments, then prepare attachment array JSON
        if (message.getAttachments() != null && message.getAttachments().size() > 0) {
        	messageJSON.setAttachments(message.getAttachments());
            
        }
        
        messageJSON.setBcc(message.getBcc());
        messageJSON.setRecipients(message.getRecipients());
        messageJSON.setAllowNonTLS(message.isAllowNonTLS());
        
        ObjectMapper mapper = new ObjectMapper();
		
		return mapper.writeValueAsString(messageJSON);
	}

	private static String getAuthorizationHeader() {
		return "Token token=" + Constants.API_KEY;
	}

}
