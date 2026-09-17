package test;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import com.paubox.config.ConfigurationManager;
import com.paubox.common.Constants;
import com.paubox.service.WebhookInterface;
import com.paubox.service.WebhookService;

public class TestWebhookService {

	static WebhookInterface webhook = new WebhookService();

	@BeforeClass
	public static void init() {
		String propertiesFile = System.getProperty("properties");
		if (propertiesFile == null || propertiesFile.equals("")) {
			propertiesFile = "src/test/config.properties";
		}
		ConfigurationManager.getProperties(propertiesFile);
	}

	private static boolean hasCredentials() {
		return Constants.API_KEY != null && !Constants.API_KEY.isEmpty()
				&& Constants.API_USER != null && !Constants.API_USER.isEmpty();
	}

	@Test
	public void testListWebhookEndpoints() throws Exception {
		if (!hasCredentials()) return;
		String response = webhook.listWebhookEndpoints();
		assertNotNull(response);
	}

	@Test
	public void testGetWebhookEndpoint() throws Exception {
		if (!hasCredentials()) return;
		String response = webhook.getWebhookEndpoint(1);
		assertNotNull(response);
	}

	@Test
	public void testDeleteWebhookEndpoint() throws Exception {
		if (!hasCredentials()) return;
		String response = webhook.deleteWebhookEndpoint(1);
		assertNotNull(response);
	}

	@Test(expected = Exception.class)
	public void testCreateWebhookEndpointRejectsNullBody() throws Exception {
		webhook.createWebhookEndpoint(null);
	}

	@Test(expected = Exception.class)
	public void testCreateWebhookEndpointRejectsEmptyBody() throws Exception {
		webhook.createWebhookEndpoint("");
	}

	@Test(expected = Exception.class)
	public void testUpdateWebhookEndpointRejectsNullBody() throws Exception {
		webhook.updateWebhookEndpoint(1, null);
	}

	@Test(expected = Exception.class)
	public void testUpdateWebhookEndpointRejectsEmptyBody() throws Exception {
		webhook.updateWebhookEndpoint(1, "");
	}

	@Test(expected = Exception.class)
	public void testCreateWebhookEndpointConvenienceRejectsNullUrl() throws Exception {
		WebhookService svc = new WebhookService();
		svc.createWebhookEndpoint(null, Arrays.asList("api_mail_log_delivered"), null, null, null);
	}

	@Test(expected = Exception.class)
	public void testCreateWebhookEndpointConvenienceRejectsEmptyUrl() throws Exception {
		WebhookService svc = new WebhookService();
		svc.createWebhookEndpoint("", Arrays.asList("api_mail_log_delivered"), null, null, null);
	}

	@Test(expected = Exception.class)
	public void testCreateWebhookEndpointConvenienceRejectsNullEvents() throws Exception {
		WebhookService svc = new WebhookService();
		svc.createWebhookEndpoint("https://example.com/webhook", null, null, null, null);
	}

	@Test(expected = Exception.class)
	public void testCreateWebhookEndpointConvenienceRejectsEmptyEvents() throws Exception {
		WebhookService svc = new WebhookService();
		svc.createWebhookEndpoint("https://example.com/webhook", Arrays.asList(), null, null, null);
	}

}
