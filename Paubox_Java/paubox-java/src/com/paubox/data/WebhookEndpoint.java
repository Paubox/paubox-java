package com.paubox.data;
 import java.util.List;

public class WebhookEndpoint {
    private int id;
    private String targetUrl;
    private List<String> events;
    private boolean active;
    private String signingKey;
    private String apiKey;
    private int apiCustomerId;

    public WebhookEndpoint(int id, String targetUrl, List<String> events, boolean active, String signingKey, String apiKey, int apiCustomerId) {
        this.id = id;
        this.targetUrl = targetUrl;
        this.events = events;
        this.active = active;
        this.signingKey = signingKey;
        this.apiKey = apiKey;
        this.apiCustomerId = apiCustomerId;
    }

    public int getId() {
        return id;
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

    public int getApiCustomerId() {
        return apiCustomerId;
    }
}