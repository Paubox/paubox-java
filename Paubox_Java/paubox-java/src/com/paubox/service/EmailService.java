package com.paubox.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paubox.common.Constants;
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

	public SendMessageResponse SendMessage(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String getAuthorizationHeader() {
		return "Token token=" + Constants.API_KEY;
	}

}
