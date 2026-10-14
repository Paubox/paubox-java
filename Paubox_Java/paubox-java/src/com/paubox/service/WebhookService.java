package com.paubox.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paubox.common.Constants;
import com.paubox.common.PauboxWebhooksException;
import com.paubox.data.CreateWebhookEndpointRequest;
import com.paubox.data.CreatedWebhookEndpoint;
import com.paubox.data.UpdateWebhookEndpointRequest;
import com.paubox.data.WebhookEndpoint;
import com.paubox.data.WebhookEndpointListRequest;
import com.paubox.data.WebhookEndpointListResponse;

/** @see WebhookInterface */
public class WebhookService implements WebhookInterface {

	/**
	 * The public gateway exposes only {@code /v1/webhooks/endpoints} and
	 * rewrites it onto the service's own {@code /v1/endpoints}, keeping the
	 * producers' event ingest route off the public host. The bare base below
	 * is never requested on its own.
	 */
	private static final String WEBHOOKS_BASE_URL = "https://api.paubox.com/v1/webhooks";

	private static final Pattern UUID_PATTERN = Pattern
			.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

	private final String apiKey;
	private final String baseUrl;
	private final WebhookTransport transport;

	public WebhookService() {
		this(Constants.WEBHOOKS_API_KEY);
	}

	public WebhookService(String apiKey) {
		this(apiKey, WEBHOOKS_BASE_URL, new ApiHelperTransport());
	}

	/** For staging, regional endpoints, and tests. */
	public WebhookService(String apiKey, String baseUrl, WebhookTransport transport) {
		this.apiKey = apiKey;
		this.baseUrl = normalizeBaseUrl(baseUrl);
		this.transport = transport;
	}

	public WebhookEndpointListResponse listWebhookEndpoints() throws Exception {
		return listWebhookEndpoints(null);
	}

	public WebhookEndpointListResponse listWebhookEndpoints(WebhookEndpointListRequest request) throws Exception {
		String url = baseUrl + "/endpoints" + buildQuery(request);
		ApiResponse response = transport.get(url, authorizationHeader());
		requireStatus(response, 200, "listWebhookEndpoints", url);
		return mapper().readValue(response.getBody(), WebhookEndpointListResponse.class);
	}

	public CreatedWebhookEndpoint createWebhookEndpoint(CreateWebhookEndpointRequest request) throws Exception {
		if (request == null) {
			throw new PauboxWebhooksException("request cannot be null.");
		}
		if (request.getTargetUrl() == null || request.getTargetUrl().isEmpty()) {
			throw new PauboxWebhooksException("target_url cannot be null or empty.");
		}
		if (request.getEvents() == null || request.getEvents().isEmpty()) {
			throw new PauboxWebhooksException("events cannot be null or empty.");
		}

		String url = baseUrl + "/endpoints";
		ApiResponse response = transport.post(url, authorizationHeader(), mapper().writeValueAsString(request));
		requireStatus(response, 201, "createWebhookEndpoint", url);
		return readData(response, url, "createWebhookEndpoint", CreatedWebhookEndpoint.class);
	}

	public WebhookEndpoint getWebhookEndpoint(String id) throws Exception {
		assertUuid(id);

		String url = baseUrl + "/endpoints/" + id;
		ApiResponse response = transport.get(url, authorizationHeader());
		requireStatus(response, 200, "getWebhookEndpoint", url);
		return readData(response, url, "getWebhookEndpoint", WebhookEndpoint.class);
	}

	public WebhookEndpoint updateWebhookEndpoint(String id, UpdateWebhookEndpointRequest request) throws Exception {
		assertUuid(id);
		if (request == null) {
			throw new PauboxWebhooksException("request cannot be null.");
		}

		String url = baseUrl + "/endpoints/" + id;
		ApiResponse response = transport.patch(url, authorizationHeader(), mapper().writeValueAsString(request));
		requireStatus(response, 200, "updateWebhookEndpoint", url);
		return readData(response, url, "updateWebhookEndpoint", WebhookEndpoint.class);
	}

	public void deleteWebhookEndpoint(String id) throws Exception {
		assertUuid(id);

		String url = baseUrl + "/endpoints/" + id;
		ApiResponse response = transport.delete(url, authorizationHeader());
		requireStatus(response, 204, "deleteWebhookEndpoint", url);
	}

	private static String normalizeBaseUrl(String baseUrl) {
		if (baseUrl == null || baseUrl.trim().isEmpty()) {
			return WEBHOOKS_BASE_URL;
		}
		return baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
	}

	/**
	 * Bearer, not the Email API's {@code Token token=}. The SDK cannot see the
	 * key's scopes; the service answers 401 or 403.
	 */
	private String authorizationHeader() throws PauboxWebhooksException {
		if (apiKey == null || apiKey.trim().isEmpty()) {
			throw new PauboxWebhooksException(
					"An API key is required. Construct WebhookService with a scoped API key, "
							+ "or set Constants.WEBHOOKS_API_KEY.");
		}
		return "Bearer " + apiKey;
	}

	/**
	 * Rejects an id that is not a UUID before it reaches a URL. Without this a
	 * value containing ".." or "/" would change which endpoint is called, and
	 * the Authorization header goes on the same host, so the key would ride
	 * along on the retargeted request.
	 */
	private static void assertUuid(String id) throws PauboxWebhooksException {
		if (id == null || !UUID_PATTERN.matcher(id).matches()) {
			throw new PauboxWebhooksException("id must be a UUID string; got: " + id);
		}
	}

	private static String buildQuery(WebhookEndpointListRequest request) {
		if (request == null) {
			return "";
		}

		List<String> parts = new ArrayList<String>();
		if (request.getPage() != null) {
			parts.add("page=" + request.getPage());
		}
		if (request.getItems() != null) {
			parts.add("items=" + request.getItems());
		}
		if (parts.isEmpty()) {
			return "";
		}

		StringBuilder query = new StringBuilder("?");
		for (int i = 0; i < parts.size(); i++) {
			if (i > 0) {
				query.append("&");
			}
			query.append(parts.get(i));
		}
		return query.toString();
	}

	/**
	 * Single resources arrive wrapped as {@code {"data": {...}}} and are
	 * unwrapped, so callers get the endpoint rather than a container holding
	 * nothing else. A list keeps its envelope — the page info beside it is
	 * what a caller paginating needs.
	 */
	private static <T> T readData(ApiResponse response, String url, String operation, Class<T> type)
			throws IOException, PauboxWebhooksException {
		ObjectMapper mapper = mapper();
		JsonNode root = mapper.readTree(response.getBody());
		JsonNode data = root == null ? null : root.get("data");

		if (data == null || data.isNull()) {
			throw new PauboxWebhooksException(operation + " returned a response without a data object.",
					response.getStatusCode(), url, response.getBody());
		}
		return mapper.treeToValue(data, type);
	}

	/**
	 * Errors arrive as {@code {"message": "..."}} and the message is promoted
	 * onto the exception, since a bare status says nothing about which field
	 * was wrong. The raw body stays on {@code getResponseBody()}.
	 */
	private static void requireStatus(ApiResponse response, int expected, String operation, String url)
			throws PauboxWebhooksException {
		if (response.getStatusCode() == expected) {
			return;
		}

		String detail = "";
		try {
			JsonNode root = mapper().readTree(response.getBody());
			if (root != null && root.hasNonNull("message")) {
				detail = ": " + root.get("message").asText();
			}
		} catch (IOException ignored) {
			// A non-JSON body (a gateway's HTML error page, say) leaves the
			// message as just the status, with the body still on the exception.
		}

		throw new PauboxWebhooksException(operation + " failed: HTTP " + response.getStatusCode() + detail,
				response.getStatusCode(), url, response.getBody());
	}

	private static ObjectMapper mapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}
}
