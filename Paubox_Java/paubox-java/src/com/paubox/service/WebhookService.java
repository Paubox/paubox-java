package com.paubox.service;

import com.paubox.common.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class WebhookService implements WebhookInterface {

	private static final String EMAIL_API_URL = "https://api.paubox.com/v1/email/";

	public String listWebhookEndpoints() throws Exception {
		String url = EMAIL_API_URL + "webhook_endpoints";
		return APIHelper.callToAPIByGet(url, getAuthorizationHeader());
	}

	public String createWebhookEndpoint(String requestBody) throws Exception {
		if (requestBody == null || requestBody.isEmpty()) {
			throw new Exception("requestBody cannot be null or empty.");
		}
		String url = EMAIL_API_URL + "webhook_endpoints";
		return APIHelper.callToAPIByPost(url, getAuthorizationHeader(), requestBody);
	}

	public String createWebhookEndpoint(String targetUrl, List<String> events,
			String signingKey, String apiKey, Boolean active) throws Exception {
		if (targetUrl == null || targetUrl.isEmpty()) {
			throw new Exception("targetUrl cannot be null or empty.");
		}
		if (events == null || events.isEmpty()) {
			throw new Exception("events cannot be null or empty.");
		}

		JSONObject requestJSON = new JSONObject();
		requestJSON.put("target_url", targetUrl);

		JSONArray eventsArray = new JSONArray();
		eventsArray.addAll(events);
		requestJSON.put("events", eventsArray);

		if (signingKey != null) {
			requestJSON.put("signing_key", signingKey);
		}
		if (apiKey != null) {
			requestJSON.put("api_key", apiKey);
		}
		if (active != null) {
			requestJSON.put("active", active);
		}

		return createWebhookEndpoint(requestJSON.toJSONString());
	}

	public String getWebhookEndpoint(int id) throws Exception {
		String url = EMAIL_API_URL + "webhook_endpoints/" + id;
		return APIHelper.callToAPIByGet(url, getAuthorizationHeader());
	}

	public String updateWebhookEndpoint(int id, String requestBody) throws Exception {
		if (requestBody == null || requestBody.isEmpty()) {
			throw new Exception("requestBody cannot be null or empty.");
		}
		String url = EMAIL_API_URL + "webhook_endpoints/" + id;
		return APIHelper.callToAPIByPatch(url, getAuthorizationHeader(), requestBody);
	}

	public String deleteWebhookEndpoint(int id) throws Exception {
		String url = EMAIL_API_URL + "webhook_endpoints/" + id;
		return APIHelper.callToAPIByDelete(url, getAuthorizationHeader());
	}

	private static String getAuthorizationHeader() {
		return "Token token=" + Constants.API_KEY;
	}

}
