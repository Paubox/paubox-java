package com.paubox.data;

public class WebhookEndpointResponse {
    private String message; 
    private WebhookEndpoint data;

    public WebhookEndpointResponse(String message, WebhookEndpoint data) {
        this.message = message;
        this.data = data;
    } 

    public String getMessage() {
        return message;
    }

    public WebhookEndpoint getData() {
        return data;
    }
}