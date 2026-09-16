package test;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import com.paubox.config.ConfigurationManager;
import com.paubox.common.Constants;
import com.paubox.service.ReceivingInterface;
import com.paubox.service.ReceivingService;

public class TestReceivingService {

	static ReceivingInterface receiving = new ReceivingService();

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
		String response = receiving.listReceivedEmails(10, "cursor_abc", null);
		assertNotNull(response);
	}

	@Test
	public void testGetReceivedEmail() throws Exception {
		if (!hasCredentials()) return;
		String response = receiving.getReceivedEmail("test-email-id");
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
		byte[] response = receiving.getReceivedEmailAttachment("test-email-id", "test-blob-id");
		assertNotNull(response);
	}

	@Test(expected = Exception.class)
	public void testGetReceivedEmailAttachmentRejectsNullEmailId() throws Exception {
		receiving.getReceivedEmailAttachment(null, "blob-id");
	}

	@Test(expected = Exception.class)
	public void testGetReceivedEmailAttachmentRejectsEmptyEmailId() throws Exception {
		receiving.getReceivedEmailAttachment("", "blob-id");
	}

	@Test(expected = Exception.class)
	public void testGetReceivedEmailAttachmentRejectsNullBlobId() throws Exception {
		receiving.getReceivedEmailAttachment("email-id", null);
	}

	@Test(expected = Exception.class)
	public void testGetReceivedEmailAttachmentRejectsEmptyBlobId() throws Exception {
		receiving.getReceivedEmailAttachment("email-id", "");
	}

}
