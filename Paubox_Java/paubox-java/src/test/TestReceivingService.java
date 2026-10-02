package test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.BeforeClass;
import org.junit.Test;

import com.paubox.config.ConfigurationManager;
import com.paubox.common.Constants;
import com.paubox.service.APIHelper;
import com.paubox.service.ReceivingInterface;
import com.paubox.service.ReceivingService;
import com.sun.net.httpserver.HttpServer;

public class TestReceivingService {

	static ReceivingInterface receiving = new ReceivingService();

	static final String EMAIL_ID = "0b6f4a8e-3c2d-4e1f-9a7b-5c8d2e1f0a3b";
	static final String ATTACHMENT_ID = "7d9e1c2b-4a5f-4b6c-8d7e-9f0a1b2c3d4e";

	@BeforeClass
	public static void init() {
		String propertiesFile = System.getProperty("properties");
		if (propertiesFile == null || propertiesFile.equals("")) {
			propertiesFile = "src/test/config.properties";
		}
		ConfigurationManager.getProperties(propertiesFile);
	}

	private static boolean hasCredentials() {
		return Constants.API_KEY != null && !Constants.API_KEY.isEmpty();
	}

	@Test
	public void testListReceivingDomains() throws Exception {
		if (!hasCredentials()) return;
		String response = receiving.listReceivingDomains();
		assertNotNull(response);
	}

	@Test
	public void testCreateReceivingDomain() throws Exception {
		if (!hasCredentials()) return;
		String response = receiving.createReceivingDomain(null);
		assertNotNull(response);
	}

	@Test
	public void testCreateReceivingDomainWithSlug() throws Exception {
		if (!hasCredentials()) return;
		String response = receiving.createReceivingDomain("test-slug");
		assertNotNull(response);
	}

	@Test
	public void testGetReceivingDomain() throws Exception {
		if (!hasCredentials()) return;
		String response = receiving.getReceivingDomain(1);
		assertNotNull(response);
	}

	@Test
	public void testDeleteReceivingDomain() throws Exception {
		if (!hasCredentials()) return;
		String response = receiving.deleteReceivingDomain(1);
		assertNotNull(response);
	}

	@Test
	public void testListReceivingMailboxes() throws Exception {
		if (!hasCredentials()) return;
		String response = receiving.listReceivingMailboxes(1);
		assertNotNull(response);
	}

	@Test
	public void testCreateReceivingMailbox() throws Exception {
		if (!hasCredentials()) return;
		String response = receiving.createReceivingMailbox(1, "test", "password123", null);
		assertNotNull(response);
	}

	@Test
	public void testCreateReceivingMailboxWithQuota() throws Exception {
		if (!hasCredentials()) return;
		String response = receiving.createReceivingMailbox(1, "test", "password123", 1073741824L);
		assertNotNull(response);
	}

	@Test(expected = Exception.class)
	public void testCreateReceivingMailboxRejectsNullName() throws Exception {
		receiving.createReceivingMailbox(1, null, "password123", null);
	}

	@Test(expected = Exception.class)
	public void testCreateReceivingMailboxRejectsEmptyName() throws Exception {
		receiving.createReceivingMailbox(1, "", "password123", null);
	}

	@Test(expected = Exception.class)
	public void testCreateReceivingMailboxRejectsNullPassword() throws Exception {
		receiving.createReceivingMailbox(1, "test", null, null);
	}

	@Test(expected = Exception.class)
	public void testCreateReceivingMailboxRejectsEmptyPassword() throws Exception {
		receiving.createReceivingMailbox(1, "test", "", null);
	}

	@Test
	public void testGetReceivingMailbox() throws Exception {
		if (!hasCredentials()) return;
		String response = receiving.getReceivingMailbox(1, 1);
		assertNotNull(response);
	}

	@Test
	public void testDeleteReceivingMailbox() throws Exception {
		if (!hasCredentials()) return;
		String response = receiving.deleteReceivingMailbox(1, 1);
		assertNotNull(response);
	}

	@Test
	public void testListReceivedEmails() throws Exception {
		if (!hasCredentials()) return;
		String response = receiving.listReceivedEmails(null, null, null);
		assertNotNull(response);
	}

	@Test
	public void testListReceivedEmailsWithParams() throws Exception {
		if (!hasCredentials()) return;
		String response = receiving.listReceivedEmails(10, EMAIL_ID, null);
		assertNotNull(response);
	}

	@Test
	public void testGetReceivedEmail() throws Exception {
		if (!hasCredentials()) return;
		String response = receiving.getReceivedEmail(EMAIL_ID);
		assertNotNull(response);
	}

	@Test(expected = Exception.class)
	public void testGetReceivedEmailRejectsNullId() throws Exception {
		receiving.getReceivedEmail(null);
	}

	@Test(expected = Exception.class)
	public void testGetReceivedEmailRejectsEmptyId() throws Exception {
		receiving.getReceivedEmail("");
	}

	@Test
	public void testGetReceivedEmailAttachment() throws Exception {
		if (!hasCredentials()) return;
		byte[] response = receiving.getReceivedEmailAttachment(EMAIL_ID, ATTACHMENT_ID);
		assertNotNull(response);
	}

	@Test
	public void testAttachmentDownloadReturnsRawBytes() throws Exception {
		final byte[] body = new byte[] { '%', 'P', 'D', 'F', '\n', 0x00, (byte) 0xFF, '\r', '\n', (byte) 0x80, '{' };
		final AtomicReference<String> requestPath = new AtomicReference<String>();
		final AtomicReference<String> accept = new AtomicReference<String>();
		HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
		server.createContext("/", exchange -> {
			requestPath.set(exchange.getRequestURI().getPath());
			accept.set(exchange.getRequestHeaders().getFirst("Accept"));
			exchange.getResponseHeaders().add("Content-Type", "application/pdf");
			exchange.getResponseHeaders().add("Content-Disposition", "attachment; filename=\"scan.pdf\"");
			exchange.sendResponseHeaders(200, body.length);
			try (OutputStream out = exchange.getResponseBody()) {
				out.write(body);
			}
		});
		server.start();
		try {
			String path = "/v1/email/receiving/" + EMAIL_ID + "/attachments/" + ATTACHMENT_ID;
			String url = "http://127.0.0.1:" + server.getAddress().getPort() + path;
			byte[] response = APIHelper.callToAPIByGetBytes(url, "Token token=test");
			assertArrayEquals(body, response);
			assertEquals(path, requestPath.get());
			assertEquals("*/*", accept.get());
		} finally {
			server.stop(0);
		}
	}

	@Test(expected = Exception.class)
	public void testGetReceivedEmailAttachmentRejectsNullEmailId() throws Exception {
		receiving.getReceivedEmailAttachment(null, ATTACHMENT_ID);
	}

	@Test(expected = Exception.class)
	public void testGetReceivedEmailAttachmentRejectsEmptyEmailId() throws Exception {
		receiving.getReceivedEmailAttachment("", ATTACHMENT_ID);
	}

	@Test(expected = Exception.class)
	public void testGetReceivedEmailAttachmentRejectsNullAttachmentId() throws Exception {
		receiving.getReceivedEmailAttachment(EMAIL_ID, null);
	}

	@Test(expected = Exception.class)
	public void testGetReceivedEmailAttachmentRejectsEmptyAttachmentId() throws Exception {
		receiving.getReceivedEmailAttachment(EMAIL_ID, "");
	}

}
