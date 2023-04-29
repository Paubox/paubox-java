package com.paubox.data;
import java.util.List;

public class WebhookEndpointRequest {
    private String targetUrl;
    private List<String> events;
    private boolean active;
    private String signingKey;
    private String apiKey;

    public WebhookEndpointRequest(String targetUrl, List<String> events, boolean active, String signingKey, String apiKey) {
        this.targetUrl = targetUrl;
        this.events = events;
        this.active = active;
        this.signingKey = signingKey;
        this.apiKey = apiKey;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public List<String> getEvents() {
        return events;
    }

    public boolean isActive() {
        return active;
    }

    public String getSigningKey() {
        return signingKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
    
    public void setEvents(List<String> events) {
        this.events = events;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }
    
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

}