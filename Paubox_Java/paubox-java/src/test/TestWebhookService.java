package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import com.paubox.common.Constants;
import com.paubox.common.PauboxWebhooksException;
import com.paubox.config.ConfigurationManager;
import com.paubox.data.CreateWebhookEndpointRequest;
import com.paubox.data.CreatedWebhookEndpoint;
import com.paubox.data.UpdateWebhookEndpointRequest;
import com.paubox.data.WebhookEndpoint;
import com.paubox.data.WebhookEndpointListResponse;
import com.paubox.service.WebhookInterface;
import com.paubox.service.WebhookService;

/**
 * Live round trip against the real service, run deliberately with credentials
 * in config.properties. Surefire excludes {@code Test*} classes; the offline
 * coverage for this client is in {@link WebhookServiceTest}.
 */
public class TestWebhookService {

	private static WebhookInterface webhook;

	@BeforeClass
	public static void init() {
		String propertiesFile = System.getProperty("properties");
		if (propertiesFile == null || propertiesFile.equals("")) {
			propertiesFile = "src/test/config.properties";
		}
		ConfigurationManager.getProperties(propertiesFile);

		// Built after the properties load, since the no-arg constructor reads
		// Constants.WEBHOOKS_API_KEY at construction time.
		webhook = new WebhookService();
	}

	private static void requireCredentials() {
		assumeTrue("WEBHOOKSAPIKEY is not set in config.properties",
				Constants.WEBHOOKS_API_KEY != null && !Constants.WEBHOOKS_API_KEY.isEmpty());
	}

	@Test
	public void testListWebhookEndpoints() throws Exception {
		requireCredentials();

		WebhookEndpointListResponse response = webhook.listWebhookEndpoints();

		assertNotNull(response);
		assertNotNull(response.getData());
		assertNotNull(response.getPageInfo());
	}

	/**
	 * One round trip rather than four independent tests: create hands back the
	 * id everything else needs, and leaving a subscription behind on a failure
	 * would send real deliveries to a URL nobody owns.
	 */
	@Test
	public void testWebhookEndpointRoundTrip() throws Exception {
		requireCredentials();

		String targetUrl = "https://example.com/java-sdk-check-" + System.currentTimeMillis();

		CreatedWebhookEndpoint created = webhook.createWebhookEndpoint(
				new CreateWebhookEndpointRequest(targetUrl, Arrays.asList("forms.submission.created")));

		try {
			assertNotNull(created.getId());
			assertEquals("active", created.getStatus());
			// Disclosed at creation and never again.
			assertTrue(created.getSigningSecret().startsWith("whsec_"));

			WebhookEndpoint fetched = webhook.getWebhookEndpoint(created.getId());
			assertEquals(targetUrl, fetched.getTargetUrl());

			UpdateWebhookEndpointRequest update = new UpdateWebhookEndpointRequest();
			update.setStatus("disabled");
			WebhookEndpoint updated = webhook.updateWebhookEndpoint(created.getId(), update);
			assertEquals("disabled", updated.getStatus());
			assertEquals(targetUrl, updated.getTargetUrl());
		} finally {
			webhook.deleteWebhookEndpoint(created.getId());
		}

		try {
			webhook.getWebhookEndpoint(created.getId());
			fail("Expected the deleted endpoint to be gone");
		} catch (PauboxWebhooksException e) {
			assertEquals(404, e.getStatusCode());
		}
	}

	@Test
	public void testDuplicateTargetUrlIsRejected() throws Exception {
		requireCredentials();

		String targetUrl = "https://example.com/java-sdk-dup-" + System.currentTimeMillis();
		CreatedWebhookEndpoint created = webhook.createWebhookEndpoint(
				new CreateWebhookEndpointRequest(targetUrl, Arrays.asList("forms.submission.created")));

		try {
			webhook.createWebhookEndpoint(
					new CreateWebhookEndpointRequest(targetUrl, Arrays.asList("forms.submission.created")));
			fail("Expected the duplicate target_url to be rejected");
		} catch (PauboxWebhooksException e) {
			assertEquals(422, e.getStatusCode());
		} finally {
			webhook.deleteWebhookEndpoint(created.getId());
		}
	}
}
