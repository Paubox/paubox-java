package com.paubox.common;

/**
 * Raised when the webhooks service refuses a request, or when a caller passes
 * something the SDK can tell is wrong before a request is made.
 *
 * <p>Previously these surfaced as a bare {@link Exception}, which left callers
 * parsing message strings to distinguish a duplicate URL from an expired key.
 * The status code and raw body are carried separately so they can be branched
 * on.</p>
 */
public class PauboxWebhooksException extends Exception {

	private static final long serialVersionUID = 1L;

	/** 0 when the request never reached the service. */
	private final int statusCode;
	private final String url;
	private final String responseBody;

	public PauboxWebhooksException(String message) {
		this(message, 0, null, null);
	}

	public PauboxWebhooksException(String message, int statusCode, String url, String responseBody) {
		super(message);
		this.statusCode = statusCode;
		this.url = url;
		this.responseBody = responseBody;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getUrl() {
		return url;
	}

	public String getResponseBody() {
		return responseBody;
	}
}
