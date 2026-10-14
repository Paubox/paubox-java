package com.paubox.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A newly created endpoint, together with its signing secret.
 *
 * <p>The service discloses {@code signing_secret} once, here. It is not on a
 * get or a list and there is no way to read it back, so recovering from a lost
 * secret means deleting the endpoint and creating another. It exists on this
 * type and nowhere else to keep that visible.</p>
 */
public class CreatedWebhookEndpoint extends WebhookEndpoint {

	@JsonProperty("signing_secret")
	private String signingSecret;

	public String getSigningSecret() { return signingSecret; }
	public void setSigningSecret(String signingSecret) { this.signingSecret = signingSecret; }
}
