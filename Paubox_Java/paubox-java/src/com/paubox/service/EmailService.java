package com.paubox.service;

import java.io.IOException;
import java.util.Base64;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paubox.common.Constants;
import com.paubox.data.Attachment;
import com.paubox.data.CancelScheduledResponse;
import com.paubox.data.GetEmailDispositionResponse;
import com.paubox.data.Message;
import com.paubox.data.MessageDeliveries;
import com.paubox.data.RescheduleResponse;
import com.paubox.data.ScheduleMessageResponse;
import com.paubox.data.ScheduledMessageStatus;
import com.paubox.data.SendMessageResponse;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class EmailService implements EmailInterface {

	private String baseApiUrl = "https://api.paubox.net/v1/" + Constants.API_USER + "/";

	public GetEmailDispositionResponse getEmailDisposition(String sourceTrackingId) throws Exception {
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

	public SendMessageResponse sendMessage(Message message) throws Exception {
		ObjectMapper mapper= new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String reqBody = formatMessage(message);
		String url = baseApiUrl + "messages";
		String responseStr = APIHelper.callToAPIByPost(url, getAuthorizationHeader(), reqBody);
		SendMessageResponse response = (SendMessageResponse) mapper.readValue(responseStr,
				SendMessageResponse.class);
		if (null == response.getData() && null == response.getSourceTrackingId() && null == response.getErrors())
        {
            throw new IOException(responseStr);
        }
		return response;
	}
	
	public ScheduleMessageResponse scheduleMessage(Message message, String scheduledAt) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		JSONObject messageJSON = buildMessageJSON(message);
		JSONObject dataJSON = new JSONObject();
		dataJSON.put("message", messageJSON);
		dataJSON.put("scheduled_at", scheduledAt);
		JSONObject requestJSON = new JSONObject();
		requestJSON.put("data", dataJSON);

		String reqBody = mapper.writeValueAsString(requestJSON);
		String url = baseApiUrl + "schedule";
		String responseStr = APIHelper.callToAPIByPost(url, getAuthorizationHeader(), reqBody);
		return mapper.readValue(responseStr, ScheduleMessageResponse.class);
	}

	public ScheduledMessageStatus getScheduledMessage(String sourceTrackingId) throws Exception {
		String url = baseApiUrl + "schedule/" + sourceTrackingId;
		String responseStr = APIHelper.callToAPIByGet(url, getAuthorizationHeader());
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper.readValue(responseStr, ScheduledMessageStatus.class);
	}

	public RescheduleResponse rescheduleMessage(String sourceTrackingId, String scheduledAt) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		JSONObject requestJSON = new JSONObject();
		requestJSON.put("scheduled_at", scheduledAt);

		String reqBody = mapper.writeValueAsString(requestJSON);
		String url = baseApiUrl + "schedule/" + sourceTrackingId;
		String responseStr = APIHelper.callToAPIByPatch(url, getAuthorizationHeader(), reqBody);
		return mapper.readValue(responseStr, RescheduleResponse.class);
	}

	public CancelScheduledResponse cancelScheduledMessage(String sourceTrackingId) throws Exception {
		String url = baseApiUrl + "schedule/" + sourceTrackingId + "/cancel";
		String responseStr = APIHelper.callToAPIByPost(url, getAuthorizationHeader(), "{}");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper.readValue(responseStr, CancelScheduledResponse.class);
	}

	private JSONObject buildMessageJSON(Message message) throws Exception {
		JSONObject messageJSON = new JSONObject();
		JSONObject contentJSON = null;
		JSONObject headerJSON = null;
		JSONObject attachmentJSON = null;
		JSONArray attachmentJSONArray = new JSONArray();

		if (message.getHeader() != null) {
			headerJSON = new JSONObject();
			headerJSON.put("subject", message.getHeader().getSubject());
			headerJSON.put("from", message.getHeader().getFrom());
			headerJSON.put("reply-to", message.getHeader().getReplyTo());
		} else {
			throw new Exception("Message Header cannot be null.");
		}

		if (message.getContent() != null) {
			contentJSON = new JSONObject();
			contentJSON.put("text/plain", message.getContent().getPlainText());
			if (message.getContent().getHtmlText() != null)
				contentJSON.put("text/html", Base64.getEncoder().encodeToString(message.getContent().getHtmlText().getBytes()));
		} else {
			throw new Exception("Message Content cannot be null.");
		}

		if (message.getAttachments() != null && message.getAttachments().size() > 0) {
			for (Attachment attachment : message.getAttachments()) {
				attachmentJSON = new JSONObject();
				attachmentJSON.put("fileName", attachment.getFileName());
				attachmentJSON.put("contentType", attachment.getContentType());
				attachmentJSON.put("content", attachment.getContent());
				attachmentJSONArray.add(attachmentJSON);
			}
		}

		messageJSON.put("bcc", message.getBcc());
		messageJSON.put("cc", message.getCc());
		messageJSON.put("recipients", message.getRecipients());
		messageJSON.put("headers", headerJSON);
		messageJSON.put("allowNonTLS", message.isAllowNonTLS());
		String forceSecureNotification = message.getForceSecureNotification();
		if (forceSecureNotification != null && !forceSecureNotification.isEmpty()) {
			if (forceSecureNotification.equalsIgnoreCase("true"))
				messageJSON.put("forceSecureNotification", true);
			else if (forceSecureNotification.equalsIgnoreCase("false"))
				messageJSON.put("forceSecureNotification", false);
		}
		messageJSON.put("content", contentJSON);
		messageJSON.put("attachments", attachmentJSONArray);

		return messageJSON;
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
			if(message.getContent().getHtmlText() != null)
				contentJSON.put("text/html",Base64.getEncoder().encodeToString(message.getContent().getHtmlText().getBytes()));
			
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
        messageJSON.put("cc",message.getCc());
        messageJSON.put("recipients",message.getRecipients());        
        messageJSON.put("headers",headerJSON);        
        messageJSON.put("allowNonTLS",message.isAllowNonTLS());
        String forceSecureNotification = message.getForceSecureNotification();
        if(forceSecureNotification != null && !forceSecureNotification.isEmpty()){
        	if(forceSecureNotification.equalsIgnoreCase("true"))
        		messageJSON.put("forceSecureNotification",true);
        	else if(forceSecureNotification.equalsIgnoreCase("false"))
        		messageJSON.put("forceSecureNotification",false);
        }
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
