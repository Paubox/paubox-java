package com.paubox.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.paubox.common.Constants;
import com.paubox.data.DynamicTemplate;

public class DynamicTemplateService {

    private String baseApiUrl = "https://api.paubox.net/v1/" + Constants.API_USER + "/";

    public List<DynamicTemplate> getAllDynamicTemplates() throws IOException {
        // Set API endpoint URL
        String url = baseApiUrl + "dynamic_templates";

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
            List<DynamicTemplate> templates = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int templateId = jsonObject.getInt("id");
                String templateName = jsonObject.getString("name");
                DynamicTemplate template = new DynamicTemplate(templateId, templateName);
                templates.add(template);
            }
            return templates;
        } else {
            throw new IOException("Paubox API Request Error: " + response.getStatusLine().getReasonPhrase());
        }
    }

    public String createDynamicTemplate(String templateName, File templateFile) throws IOException {
        String url = baseApiUrl + "dynamic_templates";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", getAuthorizationHeader());
        
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpPost.setHeader(entry.getKey(), entry.getValue());
        }
        FileBody templateBody = new FileBody(templateFile, ContentType.TEXT_PLAIN);
        HttpEntity requestEntity = MultipartEntityBuilder.create()
                .addTextBody("data[name]", templateName)
                .addPart("data[body]", templateBody)
                .build();
        httpPost.setEntity(requestEntity);
        HttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200 || statusCode == 201) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            JSONObject jsonObject = new JSONObject(responseBody);
            String message = jsonObject.getString("message");
            return "Message: " + message;
        } else {
            throw new IOException("Paubox API Request Error: " + response.getStatusLine().getReasonPhrase());
        }
    }

    public String updateDynamicTemplate(String templateId, String templateName, File templateFile) throws IOException {
        String url = baseApiUrl + "dynamic_templates/" + templateId;
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", getAuthorizationHeader());
    
        HttpClient httpClient = HttpClients.createDefault();
        HttpPatch httpPatch = new HttpPatch(url);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpPatch.setHeader(entry.getKey(), entry.getValue());
        }
        FileBody templateBody = new FileBody(templateFile, ContentType.TEXT_PLAIN);
        HttpEntity requestEntity = MultipartEntityBuilder.create()
                .addTextBody("data[name]", templateName)
                .addPart("data[body]", templateBody)
                .build();
        httpPatch.setEntity(requestEntity);
        HttpResponse response = httpClient.execute(httpPatch);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200 || statusCode == 201) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            JSONObject jsonObject = new JSONObject(responseBody);
            String message = jsonObject.getString("message");
            return "Message: " + message;
        } else {
            throw new IOException("Paubox API Request Error: " + response.getStatusLine().getReasonPhrase());
        }
    }

    public String deleteDynamicTemplate(String templateId) throws IOException {
        String url = baseApiUrl + "dynamic_templates/" + templateId;
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
            JSONObject jsonObject = new JSONObject(responseBody);
            String message = jsonObject.getString("message");
            return "Message: " + message;
        } else {
            throw new IOException("Paubox API Request Error: " + response.getStatusLine().getReasonPhrase());
        }
    }

    public DynamicTemplate getDynamicTemplate(String templateId) throws IOException {
        String url = baseApiUrl + "dynamic_templates/" + templateId;
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
            JSONObject jsonObject = new JSONObject(responseBody);
            int templateIdFromAPI = jsonObject.getInt("id");
            String templateName = jsonObject.getString("name");
            DynamicTemplate template = new DynamicTemplate(templateIdFromAPI, templateName);
            template.setContent(jsonObject.getString("body"));
            return template;
        } else {
            throw new IOException("Paubox API Request Error: " + response.getStatusLine().getReasonPhrase());
        }
    }
    
    private static String getAuthorizationHeader() {
        return "Token token=" + Constants.API_KEY;
    }
}
