package com.paubox.service;

/**
 * The status line and body of one HTTP response.
 *
 * <p>The older {@code callToAPIBy*} helpers return only the body, which is
 * enough when every interesting outcome carries one. The webhooks service
 * answers 204 with no body at all, and reports failures as a 4xx whose body is
 * a message rather than the resource, so a caller there has to see the status
 * to tell success from failure.</p>
 */
public class ApiResponse {

	private final int statusCode;
	private final String body;

	public ApiResponse(int statusCode, String body) {
		this.statusCode = statusCode;
		this.body = body;
	}

	public int getStatusCode() {
		return statusCode;
	}

	/** The raw body, or an empty string when the response had none. */
	public String getBody() {
		return body;
	}
}
