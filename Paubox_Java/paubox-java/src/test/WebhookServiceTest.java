package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.paubox.common.PauboxWebhooksException;
import com.paubox.data.CreateWebhookEndpointRequest;
import com.paubox.data.CreatedWebhookEndpoint;
import com.paubox.data.UpdateWebhookEndpointRequest;
import com.paubox.data.WebhookEndpoint;
import com.paubox.data.WebhookEndpointListRequest;
import com.paubox.data.WebhookEndpointListResponse;
import com.paubox.service.ApiResponse;
import com.paubox.service.WebhookService;
import com.paubox.service.WebhookTransport;

/**
 * Offline tests for the webhook client.
 *
 * <p>Named {@code *Test} rather than {@code Test*} so surefire runs it: the
 * live suites in this directory need credentials and stay excluded. Nothing
 * here touches the network.</p>
 */
public class WebhookServiceTest {

	private static final String BASE = "https://api.paubox.com/v1/webhooks";
	private static final String ID = "2ec66c21-bf48-48eb-8d28-f80b2d6b77c7";

	/** Records the call it was given and replays a canned response. */
	private static class RecordingTransport implements WebhookTransport {
		final List<String> verbs = new ArrayList<String>();
		final List<String> urls = new ArrayList<String>();
		final List<String> auths = new ArrayList<String>();
		final List<String> bodies = new ArrayList<String>();
		private final ApiResponse response;

		RecordingTransport(int status, String body) {
			this.response = new ApiResponse(status, body);
		}

		public ApiResponse get(String url, String auth) {
			return record("GET", url, auth, null);
		}

		public ApiResponse post(String url, String auth, String body) {
			return record("POST", url, auth, body);
		}

		public ApiResponse patch(String url, String auth, String body) {
			return record("PATCH", url, auth, body);
		}

		public ApiResponse delete(String url, String auth) {
			return record("DELETE", url, auth, null);
		}

		private ApiResponse record(String verb, String url, String auth, String body) {
			verbs.add(verb);
			urls.add(url);
			auths.add(auth);
			bodies.add(body);
			return response;
		}
	}

	private static String endpointJson(String extra) {
		return "{\"id\":\"" + ID + "\","
				+ "\"target_url\":\"https://hooks.example.com/paubox\","
				+ "\"status\":\"active\","
				+ "\"events\":[\"forms.submission.created\"],"
				+ "\"created_at\":\"2026-10-09T01:43:41.639968+00:00\","
				+ "\"updated_at\":\"2026-10-09T01:43:41.639968+00:00\"" + extra + "}";
	}

	private static WebhookService service(RecordingTransport transport) {
		return new WebhookService("test-key", BASE, transport);
	}

	// --- list -------------------------------------------------------------

	@Test
	public void listReturnsRowsAndPageInfo() throws Exception {
		RecordingTransport transport = new RecordingTransport(200,
				"{\"data\":[" + endpointJson("") + "],\"page_info\":{\"count\":7,\"items\":1}}");

		WebhookEndpointListResponse result = service(transport).listWebhookEndpoints();

		assertEquals(1, result.getData().size());
		assertEquals(ID, result.getData().get(0).getId());
		assertEquals("active", result.getData().get(0).getStatus());
		// count is the total matching, not the length of this page.
		assertEquals(7L, result.getPageInfo().getCount());
		assertEquals(1, result.getPageInfo().getItems());
	}

	@Test
	public void listSendsBearerAuthToTheEndpointsResource() throws Exception {
		RecordingTransport transport = new RecordingTransport(200,
				"{\"data\":[],\"page_info\":{\"count\":0,\"items\":0}}");

		service(transport).listWebhookEndpoints();

		assertEquals("GET", transport.verbs.get(0));
		assertEquals(BASE + "/endpoints", transport.urls.get(0));
		// Bearer, not the Email API's "Token token=".
		assertEquals("Bearer test-key", transport.auths.get(0));
	}

	@Test
	public void listSendsPaginationWhenGiven() throws Exception {
		RecordingTransport transport = new RecordingTransport(200,
				"{\"data\":[],\"page_info\":{\"count\":0,\"items\":0}}");
		WebhookEndpointListRequest request = new WebhookEndpointListRequest();
		request.setPage(2);
		request.setItems(25);

		service(transport).listWebhookEndpoints(request);

		assertEquals(BASE + "/endpoints?page=2&items=25", transport.urls.get(0));
	}

	@Test
	public void listOmitsPaginationWhenUnset() throws Exception {
		RecordingTransport transport = new RecordingTransport(200,
				"{\"data\":[],\"page_info\":{\"count\":0,\"items\":0}}");

		service(transport).listWebhookEndpoints(new WebhookEndpointListRequest());

		assertEquals(BASE + "/endpoints", transport.urls.get(0));
	}

	// --- create -----------------------------------------------------------

	@Test
	public void createReturnsTheSigningSecret() throws Exception {
		RecordingTransport transport = new RecordingTransport(201,
				"{\"data\":" + endpointJson(",\"signing_secret\":\"whsec_abc123\"")
						+ ",\"message\":\"Store this signing_secret now\"}");

		CreatedWebhookEndpoint created = service(transport).createWebhookEndpoint(
				new CreateWebhookEndpointRequest("https://hooks.example.com/paubox",
						Arrays.asList("forms.submission.created")));

		assertEquals("whsec_abc123", created.getSigningSecret());
		assertEquals(ID, created.getId());
	}

	@Test
	public void createPostsTargetUrlAndEvents() throws Exception {
		RecordingTransport transport = new RecordingTransport(201, "{\"data\":" + endpointJson("") + "}");

		service(transport).createWebhookEndpoint(new CreateWebhookEndpointRequest(
				"https://hooks.example.com/paubox", Arrays.asList("forms.submission.created")));

		assertEquals("POST", transport.verbs.get(0));
		assertEquals(BASE + "/endpoints", transport.urls.get(0));
		assertTrue(transport.bodies.get(0).contains("\"target_url\":\"https://hooks.example.com/paubox\""));
		assertTrue(transport.bodies.get(0).contains("\"events\":[\"forms.submission.created\"]"));
	}

	/**
	 * The catalog belongs to the service and grows without an SDK release, so
	 * an unrecognised name must reach the server rather than be rejected here.
	 */
	@Test
	public void createDoesNotValidateEventNames() throws Exception {
		RecordingTransport transport = new RecordingTransport(201, "{\"data\":" + endpointJson("") + "}");

		service(transport).createWebhookEndpoint(new CreateWebhookEndpointRequest(
				"https://e.test/h", Arrays.asList("some.future.event")));

		assertTrue(transport.bodies.get(0).contains("some.future.event"));
	}

	@Test
	public void createRejectsBadRequestsBeforeAnyCall() {
		List<CreateWebhookEndpointRequest> invalid = Arrays.asList(
				null,
				new CreateWebhookEndpointRequest(null, Arrays.asList("forms.submission.created")),
				new CreateWebhookEndpointRequest("", Arrays.asList("forms.submission.created")),
				new CreateWebhookEndpointRequest("https://e.test/h", null),
				new CreateWebhookEndpointRequest("https://e.test/h", new ArrayList<String>()));

		for (CreateWebhookEndpointRequest request : invalid) {
			RecordingTransport transport = new RecordingTransport(201, "{}");
			try {
				service(transport).createWebhookEndpoint(request);
				fail("Expected PauboxWebhooksException");
			} catch (PauboxWebhooksException expected) {
				assertTrue(transport.verbs.isEmpty());
			} catch (Exception e) {
				fail("Expected PauboxWebhooksException, got " + e);
			}
		}
	}

	// --- get --------------------------------------------------------------

	@Test
	public void getUnwrapsTheDataEnvelope() throws Exception {
		RecordingTransport transport = new RecordingTransport(200, "{\"data\":" + endpointJson("") + "}");

		WebhookEndpoint endpoint = service(transport).getWebhookEndpoint(ID);

		assertEquals(ID, endpoint.getId());
		assertEquals("https://hooks.example.com/paubox", endpoint.getTargetUrl());
		assertEquals(BASE + "/endpoints/" + ID, transport.urls.get(0));
	}

	@Test
	public void getThrowsWhenTheEnvelopeIsMissing() throws Exception {
		RecordingTransport transport = new RecordingTransport(200, "{}");

		try {
			service(transport).getWebhookEndpoint(ID);
			fail("Expected PauboxWebhooksException");
		} catch (PauboxWebhooksException e) {
			assertTrue(e.getMessage().contains("without a data object"));
		}
	}

	/**
	 * A non-UUID id must be refused before a request. Without the guard a
	 * value containing ".." or "/" would change which endpoint is called, and
	 * the Authorization header goes on the same host, so the key would ride
	 * along on the retargeted request.
	 */
	@Test
	public void getRejectsBadIdsBeforeAnyCall() {
		String[] bad = { null, "", "   ", "abc", "../endpoints", ID + "/x", ID + "%2F..", "1" };

		for (String id : bad) {
			RecordingTransport transport = new RecordingTransport(200, "{}");
			try {
				service(transport).getWebhookEndpoint(id);
				fail("Expected PauboxWebhooksException for id: " + id);
			} catch (PauboxWebhooksException expected) {
				assertTrue("no request should have been made for id: " + id, transport.verbs.isEmpty());
			} catch (Exception e) {
				fail("Expected PauboxWebhooksException, got " + e);
			}
		}
	}

	// --- update -----------------------------------------------------------

	@Test
	public void updateSendsOnlyTheFieldsSet() throws Exception {
		RecordingTransport transport = new RecordingTransport(200,
				"{\"data\":{\"id\":\"" + ID + "\",\"target_url\":\"https://hooks.example.com/paubox\","
						+ "\"status\":\"disabled\",\"events\":[\"forms.submission.created\"],"
						+ "\"created_at\":\"2026-10-09T01:43:41.639968+00:00\","
						+ "\"updated_at\":\"2026-10-09T02:00:00.000000+00:00\"}}");

		UpdateWebhookEndpointRequest request = new UpdateWebhookEndpointRequest();
		request.setStatus("disabled");

		WebhookEndpoint updated = service(transport).updateWebhookEndpoint(ID, request);

		assertEquals("disabled", updated.getStatus());
		assertEquals("PATCH", transport.verbs.get(0));
		// target_url and events are omitted, not sent as null.
		assertTrue(transport.bodies.get(0).contains("status"));
		assertFalse(transport.bodies.get(0).contains("target_url"));
		assertFalse(transport.bodies.get(0).contains("events"));
	}

	@Test
	public void updateRejectsANullRequest() {
		RecordingTransport transport = new RecordingTransport(200, "{}");
		try {
			service(transport).updateWebhookEndpoint(ID, null);
			fail("Expected PauboxWebhooksException");
		} catch (PauboxWebhooksException expected) {
			assertTrue(transport.verbs.isEmpty());
		} catch (Exception e) {
			fail("Expected PauboxWebhooksException, got " + e);
		}
	}

	// --- delete -----------------------------------------------------------

	/**
	 * The service answers 204 with no body, so there is nothing to parse —
	 * an empty body must not be read as a failure.
	 */
	@Test
	public void deleteAcceptsAnEmpty204() throws Exception {
		RecordingTransport transport = new RecordingTransport(204, "");

		service(transport).deleteWebhookEndpoint(ID);

		assertEquals("DELETE", transport.verbs.get(0));
		assertEquals(BASE + "/endpoints/" + ID, transport.urls.get(0));
	}

	@Test
	public void deleteSurfacesA404() throws Exception {
		RecordingTransport transport = new RecordingTransport(404, "{\"message\":\"webhook endpoint not found\"}");

		try {
			service(transport).deleteWebhookEndpoint(ID);
			fail("Expected PauboxWebhooksException");
		} catch (PauboxWebhooksException e) {
			assertEquals(404, e.getStatusCode());
		}
	}

	// --- errors -----------------------------------------------------------

	/**
	 * A bare status says nothing about which field was wrong, so the service's
	 * message is promoted onto the exception while the raw body stays on
	 * getResponseBody().
	 */
	@Test
	public void errorPromotesTheServiceMessage() throws Exception {
		RecordingTransport transport = new RecordingTransport(422,
				"{\"message\":\"target_url: an endpoint already exists for this URL\"}");

		try {
			service(transport).createWebhookEndpoint(new CreateWebhookEndpointRequest(
					"https://hooks.example.com/paubox", Arrays.asList("forms.submission.created")));
			fail("Expected PauboxWebhooksException");
		} catch (PauboxWebhooksException e) {
			assertEquals(422, e.getStatusCode());
			assertTrue(e.getMessage().contains("an endpoint already exists"));
			assertTrue(e.getResponseBody().contains("target_url"));
			assertEquals(BASE + "/endpoints", e.getUrl());
		}
	}

	@Test
	public void errorSurvivesANonJsonBody() throws Exception {
		RecordingTransport transport = new RecordingTransport(502, "<html>bad gateway</html>");

		try {
			service(transport).listWebhookEndpoints();
			fail("Expected PauboxWebhooksException");
		} catch (PauboxWebhooksException e) {
			assertEquals(502, e.getStatusCode());
			assertTrue(e.getMessage().contains("HTTP 502"));
			assertTrue(e.getResponseBody().contains("bad gateway"));
		}
	}

	// --- configuration ----------------------------------------------------

	@Test
	public void missingApiKeyIsRefusedBeforeAnyCall() {
		RecordingTransport transport = new RecordingTransport(200, "{}");
		try {
			new WebhookService(null, BASE, transport).listWebhookEndpoints();
			fail("Expected PauboxWebhooksException");
		} catch (PauboxWebhooksException expected) {
			assertTrue(transport.verbs.isEmpty());
		} catch (Exception e) {
			fail("Expected PauboxWebhooksException, got " + e);
		}
	}

	@Test
	public void baseUrlIsOverridableAndTrimmed() throws Exception {
		RecordingTransport transport = new RecordingTransport(200,
				"{\"data\":[],\"page_info\":{\"count\":0,\"items\":0}}");

		new WebhookService("k", "https://api.staging.paubox.net/v1/webhooks/", transport)
				.listWebhookEndpoints();

		assertEquals("https://api.staging.paubox.net/v1/webhooks/endpoints", transport.urls.get(0));
	}

	/** An unknown field in the response must not break deserialization. */
	@Test
	public void unknownResponseFieldsAreIgnored() throws Exception {
		RecordingTransport transport = new RecordingTransport(200,
				"{\"data\":" + endpointJson(",\"some_future_field\":\"x\"") + "}");

		WebhookEndpoint endpoint = service(transport).getWebhookEndpoint(ID);

		assertEquals(ID, endpoint.getId());
	}

	/** A get must not carry a signing secret; only create discloses one. */
	@Test
	public void getCarriesNoSigningSecret() throws Exception {
		RecordingTransport transport = new RecordingTransport(200, "{\"data\":" + endpointJson("") + "}");

		WebhookEndpoint endpoint = service(transport).getWebhookEndpoint(ID);

		assertFalse(endpoint instanceof CreatedWebhookEndpoint);
		assertNull(transport.bodies.get(0));
	}
}
