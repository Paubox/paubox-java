package com.paubox.service;

/**
 * The HTTP calls {@link WebhookService} makes.
 *
 * <p>{@link APIHelper} is entirely static, so nothing built on it could be
 * exercised without reaching the network — which is why every test in this
 * repo needs credentials and is skipped by default. This interface is the seam
 * that lets the webhook client be tested offline; production always gets
 * {@link ApiHelperTransport}.</p>
 */
public interface WebhookTransport {

	ApiResponse get(String url, String authHeader) throws Exception;

	ApiResponse post(String url, String authHeader, String requestBody) throws Exception;

	ApiResponse patch(String url, String authHeader, String requestBody) throws Exception;

	ApiResponse delete(String url, String authHeader) throws Exception;
}
