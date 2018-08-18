package com.paubox.service;

import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paubox.common.Constants;
import com.paubox.data.Attachment;
import com.paubox.data.GetEmailDispositionResponse;
import com.paubox.data.Message;
import com.paubox.data.MessageDeliveries;
import com.paubox.data.SendMessageResponse;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class EmailService implements EmailInterface {

	private String baseApiUrl = "https://api.paubox.net/v1/" + Constants.API_USER + "/";

	public GetEmailDispositionResponse GetEmailDisposition(String sourceTrackingId) throws Exception {
		String url = baseApiUrl + "message_receipt?sourceTrackingId=" + sourceTrackingId;
		String responseStr = APIHelper.callToAPIByGet(url, getAuthorizationHeader());
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		GetEmailDispositionResponse response = (GetEmailDispositionResponse) mapper.readValue(responseStr,
				GetEmailDispositionResponse.class);
		 if (response.getData() == null && response.getSourceTrackingId() == null && response.getErrors() == null)
         {
			 throw new IOException(responseStr);
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

	public SendMessageResponse SendMessage(Message message) throws Exception {
		ObjectMapper mapper= new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String reqBody = formatMessage(message);
		System.out.println(reqBody);
		String url = baseApiUrl + "messages";
		String responseStr = APIHelper.callToAPIByPost(url, getAuthorizationHeader(), reqBody);
		SendMessageResponse response = (SendMessageResponse) mapper.readValue(responseStr,
				SendMessageResponse.class);
		if (response.getData() == null && response.getSourceTrackingId() == null && response.getErrors() == null)
        {
            throw new IOException(responseStr);
        }
		return response;
	}
	
	private String formatMessage(Message message) throws Exception{
						
		JSONObject messageJSON = new JSONObject();		
		JSONObject contentJSON = null;
		JSONObject headerJSON = null;
		JSONObject attachmentJSON = null;		
        JSONObject requestJSON = new JSONObject();
        JSONObject dataJSON = new JSONObject();
        JSONArray attachmentJSONArray = new JSONArray();
		
		if (message.getHeader() != null) {
			
			headerJSON = new JSONObject();
			headerJSON.put("subject" , message.getHeader().getSubject());
			headerJSON.put("from" , message.getHeader().getFrom());
			headerJSON.put("reply-to" , message.getHeader().getReplyTo());						
        }
        else {
            throw new Exception("Message Header cannot be null.");
        }
		
		if (message.getContent() != null){
			
			contentJSON = new JSONObject();
			contentJSON.put("text/plain" , message.getContent().getPlainText());
			contentJSON.put("text/html" , message.getContent().getHtmlText());			
			
        } else {
            throw new Exception("Message Content cannot be null.");
        }
		
		  //If there are attachments, then prepare attachment array JSON
        if (message.getAttachments() != null && message.getAttachments().size() > 0) {        	           
            for (Attachment attachment : message.getAttachments())
            {
            	attachmentJSON = new JSONObject();
            	attachmentJSON.put("fileName" , attachment.getFileName());
            	attachmentJSON.put("contentType" , attachment.getContentType());
            	attachmentJSON.put("content" , attachment.getContent());
    			    			               
            	attachmentJSONArray.add(attachmentJSON);
            }        	
        }
        
        messageJSON.put("bcc",message.getBcc());                                
        messageJSON.put("recipients",message.getRecipients());        
        messageJSON.put("headers",headerJSON);        
        messageJSON.put("allowNonTLS",message.isAllowNonTLS());
        messageJSON.put("content",contentJSON);
        messageJSON.put("attachments",attachmentJSONArray);
        
        dataJSON.put("message",messageJSON);
        requestJSON.put("data",dataJSON);
        
        ObjectMapper mapper = new ObjectMapper();		
		return mapper.writeValueAsString(requestJSON);
	}

	private static String getAuthorizationHeader() {
		return "Token token=" + Constants.API_KEY;
	}

}
