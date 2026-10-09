package com.paubox.service;

/** The real transport: a thin pass-through to {@link APIHelper}. */
public class ApiHelperTransport implements WebhookTransport {

	public ApiResponse get(String url, String authHeader) throws Exception {
		return APIHelper.callToAPIByGetWithResponse(url, authHeader);
	}

	public ApiResponse post(String url, String authHeader, String requestBody) throws Exception {
		return APIHelper.callToAPIByPostWithResponse(url, authHeader, requestBody);
	}

	public ApiResponse patch(String url, String authHeader, String requestBody) throws Exception {
		return APIHelper.callToAPIByPatchWithResponse(url, authHeader, requestBody);
	}

	public ApiResponse delete(String url, String authHeader) throws Exception {
		return APIHelper.callToAPIByDeleteWithResponse(url, authHeader);
	}
}
