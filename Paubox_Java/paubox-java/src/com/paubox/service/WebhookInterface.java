package com.paubox.service;

public interface WebhookInterface {

	public String listWebhookEndpoints() throws Exception;

	public String createWebhookEndpoint(String requestBody) throws Exception;

	public String getWebhookEndpoint(int id) throws Exception;

	public String updateWebhookEndpoint(int id, String requestBody) throws Exception;

	public String deleteWebhookEndpoint(int id) throws Exception;

}
