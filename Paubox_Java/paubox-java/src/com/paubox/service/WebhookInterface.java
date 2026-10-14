package com.paubox.service;

import com.paubox.common.PauboxWebhooksException;
import com.paubox.data.CreateWebhookEndpointRequest;
import com.paubox.data.CreatedWebhookEndpoint;
import com.paubox.data.UpdateWebhookEndpointRequest;
import com.paubox.data.WebhookEndpoint;
import com.paubox.data.WebhookEndpointListRequest;
import com.paubox.data.WebhookEndpointListResponse;

/**
 * Manages webhook subscriptions on the Paubox webhooks service: which URL
 * Paubox notifies, and for which events.
 *
 * <p>Authenticates with a scoped API key sent as
 * {@code Authorization: Bearer <key>} — a different scheme from the Email
 * API's {@code Token token=}. Which events a key may subscribe to follows from
 * its scopes, which the SDK does not inspect.</p>
 */
public interface WebhookInterface {

	/**
	 * Lists the endpoints this key can act on. Endpoints carrying an event the
	 * key is not scoped for are filtered out by the service.
	 */
	WebhookEndpointListResponse listWebhookEndpoints() throws Exception;

	/** As above, with pagination. */
	WebhookEndpointListResponse listWebhookEndpoints(WebhookEndpointListRequest request) throws Exception;

	/**
	 * Subscribes a URL to one or more events. The result carries the signing
	 * secret, which the service returns only here.
	 */
	CreatedWebhookEndpoint createWebhookEndpoint(CreateWebhookEndpointRequest request) throws Exception;

	/**
	 * Retrieves a single endpoint by its UUID. The response does not include
	 * the signing secret.
	 */
	WebhookEndpoint getWebhookEndpoint(String id) throws Exception;

	/** Updates an endpoint; only the fields set on the request are sent. */
	WebhookEndpoint updateWebhookEndpoint(String id, UpdateWebhookEndpointRequest request) throws Exception;

	/**
	 * Deletes an endpoint, stopping every event on it. Returns nothing: the
	 * service answers 204 with no body.
	 *
	 * @throws PauboxWebhooksException if the endpoint does not exist
	 */
	void deleteWebhookEndpoint(String id) throws Exception;
}
