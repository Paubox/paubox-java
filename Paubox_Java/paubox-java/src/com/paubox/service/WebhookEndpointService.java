package com.paubox.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paubox.common.Constants;
import com.paubox.data.WebhookEndpoint;
import com.paubox.data.WebhookEndpointRequest;
import com.paubox.data.WebhookEndpointResponse;

public class WebhookEndpointService {

    private String baseApiUrl = "https://api.paubox.net/v1/" + Constants.API_USER + "/";

    public List<WebhookEndpoint> getAllWebhookEndpoints() throws IOException {
        // Set API endpoint URL
        String url = baseApiUrl + "webhook_endpoints";

        // Set request headers
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", getAuthorizationHeader());

        // Create HTTP client and request object
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        // headers.forEach(httpGet::setHeader);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpGet.setHeader(entry.getKey(), entry.getValue());
        }

        // Make API request
        HttpResponse response = httpClient.execute(httpGet);

        // Handle API response
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200 || statusCode == 201) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            JSONArray jsonArray = new JSONArray(responseBody);
            List<WebhookEndpoint> endpoints = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String targetUrl = jsonObject.getString("target_url");
                List<String> events = new ArrayList<>();
                JSONArray eventsArray = jsonObject.getJSONArray("events");
                for (int j = 0; j < eventsArray.length(); j++) {
                    events.add(eventsArray.getString(j));
                }
                boolean active = jsonObject.getBoolean("active");
                String signingKey = jsonObject.optString("signing_key", null);
                String apiKey = jsonObject.optString("api_key", null);
                int apiCustomerId = jsonObject.optInt("api_customer_id", 0);
                WebhookEndpoint endpoint = new WebhookEndpoint(id, targetUrl, events, active, signingKey, apiKey, apiCustomerId);
                endpoints.add(endpoint);
            }
            return endpoints;
        } else {
            throw new IOException("Paubox API Request Error: " + response.getStatusLine().getReasonPhrase());
        }
    }

    public WebhookEndpointResponse createWebhookEndpoint(WebhookEndpointRequest request) throws IOException {
        String url = baseApiUrl + "webhook_endpoints";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", getAuthorizationHeader());
        
        List<NameValuePair> requestBody = new ArrayList<>();
        requestBody.add(new BasicNameValuePair("target_url", request.getTargetUrl()));
        requestBody.add(new BasicNameValuePair("events", String.join(",", request.getEvents())));
        requestBody.add(new BasicNameValuePair("active", String.valueOf(request.isActive())));        
        requestBody.add(new BasicNameValuePair("signing_key", request.getSigningKey()));
        requestBody.add(new BasicNameValuePair("api_key", request.getApiKey()));
        
        HttpEntity requestBodyEntity = new UrlEncodedFormEntity(requestBody, StandardCharsets.UTF_8);

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpPost.setHeader(entry.getKey(), entry.getValue());
        }        
        httpPost.setEntity(requestBodyEntity);

        HttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200 || statusCode == 201) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
             
            ObjectMapper objectMapper = new ObjectMapper();
            WebhookEndpointResponse webhookCreationResponse = objectMapper.readValue(responseBody, WebhookEndpointResponse.class);
            return webhookCreationResponse;
        } else {
            throw new IOException("Paubox API Request Error: " + response.getStatusLine().getReasonPhrase());
        }
    }

    public WebhookEndpointResponse updateWebhookEndpoint(String endpointId, WebhookEndpointRequest request) throws IOException {
        String url = baseApiUrl + "webhook_endpoints/" + endpointId;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", getAuthorizationHeader());
    
        List<NameValuePair> requestBody = new ArrayList<>();
        requestBody.add(new BasicNameValuePair("target_url", request.getTargetUrl()));
        requestBody.add(new BasicNameValuePair("events", String.join(",", request.getEvents())));
        requestBody.add(new BasicNameValuePair("active", String.valueOf(request.isActive())));        
        requestBody.add(new BasicNameValuePair("signing_key", request.getSigningKey()));
        requestBody.add(new BasicNameValuePair("api_key", request.getApiKey()));
        HttpEntity requestBodyEntity = new UrlEncodedFormEntity(requestBody, StandardCharsets.UTF_8);

        HttpClient httpClient = HttpClients.createDefault();
        HttpPatch httpPatch = new HttpPatch(url);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpPatch.setHeader(entry.getKey(), entry.getValue());
        }
         
        httpPatch.setEntity(requestBodyEntity);
        HttpResponse response = httpClient.execute(httpPatch);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200 || statusCode == 201) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            ObjectMapper objectMapper = new ObjectMapper();
            WebhookEndpointResponse webhookResponse = objectMapper.readValue(responseBody, WebhookEndpointResponse.class);
            return webhookResponse;
        } else {
            throw new IOException("Paubox API Request Error: " + response.getStatusLine().getReasonPhrase());
        }
    }

    public WebhookEndpointResponse deleteWebhookEndpoint(String templateId) throws IOException {
        String url = baseApiUrl + "webhook_endpoints/" + templateId;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", getAuthorizationHeader());
    
        HttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(url);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpDelete.setHeader(entry.getKey(), entry.getValue());
        }
        HttpResponse response = httpClient.execute(httpDelete);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200 || statusCode == 201) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            ObjectMapper objectMapper = new ObjectMapper();
            WebhookEndpointResponse webhookResponse = objectMapper.readValue(responseBody, WebhookEndpointResponse.class);
            return webhookResponse;
        } else {
            throw new IOException("Paubox API Request Error: " + response.getStatusLine().getReasonPhrase());
        }
    }

    public WebhookEndpoint getWebhookEndpoint(String templateId) throws IOException {
        String url = baseApiUrl + "webhook_endpoints/" + templateId;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", getAuthorizationHeader());
    
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpGet.setHeader(entry.getKey(), entry.getValue());
        }
        HttpResponse response = httpClient.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200 || statusCode == 201) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            ObjectMapper objectMapper = new ObjectMapper();
            WebhookEndpoint WebhookEndpoint = objectMapper.readValue(responseBody, WebhookEndpoint.class);
            return WebhookEndpoint;
        } else {
            throw new IOException("Paubox API Request Error: " + response.getStatusLine().getReasonPhrase());
        }
    }
    
    private static String getAuthorizationHeader() {
        return "Token token=" + Constants.API_KEY;
    }
}
