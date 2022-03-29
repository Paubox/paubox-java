package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.paubox.config.ConfigurationManager;
import com.paubox.data.Attachment;
import com.paubox.data.Content;
import com.paubox.data.GetEmailDispositionResponse;
import com.paubox.data.Header;
import com.paubox.data.Message;
import com.paubox.data.SendMessageResponse;
import com.paubox.service.EmailInterface;
import com.paubox.service.EmailService;

public class TestEmailService {
	EmailInterface email = new EmailService();
	 Message message =new Message();
	
	@BeforeClass
	public static void  init(){
		ConfigurationManager.getProperties("E:\\projects\\paubox-java\\Paubox_Java\\PauboxTest\\src\\resources\\config.properties");
	}
	
	@Before
	public void getMessage()
    {
        message.setRecipients(new String[] { "test@test.com" });

        Content content = new Content();
        Header header = new Header();
        Attachment attachment = new Attachment();
        List<Attachment> listAttachments = new ArrayList<Attachment>();
        attachment.setFileName("hello_world.txt");
        attachment.setContentType("text/plain");
        attachment.setContent("SGVsbG8gV29ybGQh\n");

        listAttachments.add(attachment);


        header.setSubject("Test Mail from java");
        header.setFrom("renee@undefeatedgames.com");
        content.setPlainText("Hello Again");

        message.setHeader(header);
        message.setContent(content);
        message.setAttachments(listAttachments);
        
    }
	
	@Test
	public void testGetEmailDispositionForSuccess() {
		try {
			GetEmailDispositionResponse response = email.getEmailDisposition("97b18032-59d5-47c7-a7c6-a2ed27f0f44e");
			assertNotNull(response);
			assertNotNull(response.getSourceTrackingId());
			assertNotNull(response.getData());
			assertNull(response.getErrors());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void testGetEmailDispositionForTrckingID() throws Exception {
		// tracking iD is null
			GetEmailDispositionResponse response = email.getEmailDisposition(null);
			assertNotNull(response);
			assertEquals("null", response.getSourceTrackingId());
			assertNull(response.getData());
			assertNotNull(response.getErrors());
			assertEquals(404, response.getErrors().get(0).getCode());
			
			// tracking id is empty
			GetEmailDispositionResponse emptyResponse = email.getEmailDisposition("");
			assertNotNull(emptyResponse);
			assertNull(emptyResponse.getSourceTrackingId());
			assertNull(emptyResponse.getData());
			assertNotNull(emptyResponse.getErrors());
			assertEquals(400, emptyResponse.getErrors().get(0).getCode());
			
			//invalid tracking id
			GetEmailDispositionResponse responseGarbage = email.getEmailDisposition("garbagevalue");
			assertNotNull(responseGarbage);
			assertEquals("garbagevalue", responseGarbage.getSourceTrackingId());
			assertNull(responseGarbage.getData());
			assertNotNull(responseGarbage.getErrors());
			assertEquals(404, responseGarbage.getErrors().get(0).getCode());
	}
	
	@Test(expected = Exception.class)
	public void testGetEmailDispositionForWhiteSpace() throws Exception {
		// tracking id is white space
		GetEmailDispositionResponse spaceResponse = email.getEmailDisposition(" ");
	}
	
	
	@Test
	public void testSendMessageForSuccess() {
		try {
			SendMessageResponse response = email.sendMessage(message);
			assertNotNull(response);
			assertNotNull(response.getSourceTrackingId());
			assertNotNull(response.getData());
			assertNull(response.getErrors());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void testSendMessageForOptionalValue() {
		try {
			message.setBcc(null);
			message.setCc(null);
			message.setAttachments(null);
			SendMessageResponse response = email.sendMessage(message);
			assertNotNull(response);
			assertNotNull(response.getSourceTrackingId());
			assertNotNull(response.getData());
			assertNull(response.getErrors());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void testSendMessageForErrorMail() {
		try {
            message.setRecipients(new String[] { "bogus@undefeatedgames123.@com" });
			SendMessageResponse response = email.sendMessage(message);
			assertNotNull(response);
			assertNull(response.getSourceTrackingId());
			assertNull(response.getData());
			assertNotNull(response.getErrors());
			assertEquals(400, response.getErrors().get(0).getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void testSendMessageForContent() {
		try {
			message.setAttachments(null);
			Attachment attachment = new Attachment();
			List<Attachment> listAttachments = new ArrayList<Attachment>();
	        attachment.setFileName("hello_world.txt");
	        attachment.setContentType("text/html");
	        attachment.setContent("SGVsbG8gV29ybGQh\n");
	        listAttachments.add(attachment);
	        message.getContent().setPlainText(null);
	        message.getContent().setHtmlText("text/html");
	        message.setAttachments(listAttachments);
			SendMessageResponse response = email.sendMessage(message);
			assertNotNull(response);
			assertNotNull(response.getSourceTrackingId());
			assertNotNull(response.getData());
			assertNull(response.getErrors());
			
			Attachment attachment1 = new Attachment();
			List<Attachment> listAttachments1 = new ArrayList<Attachment>();
	        attachment1.setFileName("hello_world.txt");
	        attachment1.setContentType("text/html");
	        attachment1.setContent("SGVsbG8gV29ybGQh\n");
	        listAttachments1.add(attachment1);
	        message.getContent().setHtmlText(null);
	        message.getContent().setPlainText("text/Plain");
	        message.setAttachments(listAttachments1);
			SendMessageResponse response1 = email.sendMessage(message);
			assertNotNull(response1);
			assertNotNull(response1.getSourceTrackingId());
			assertNotNull(response1.getData());
			assertNull(response1.getErrors());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void testSendMessageForAttachment() {
		try {
			message.setAttachments(null);
			Attachment attachment = new Attachment();
			List<Attachment> listAttachments = new ArrayList<Attachment>();
	        attachment.setFileName("hello_world.txt");
	        attachment.setContentType("text12/html");
	        attachment.setContent("SGVsbG8gV29ybGQh\n");
	        listAttachments.add(attachment);
	        message.getContent().setPlainText(null);
	        message.getContent().setHtmlText("text/html");
	        message.setAttachments(listAttachments);
			SendMessageResponse response1 = email.sendMessage(message);
			assertNotNull(response1);
			assertNotNull(response1.getSourceTrackingId());
			assertNotNull(response1.getData());
			assertNull(response1.getErrors());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	@Test
	public void testSendMessageForPDFAttachment() {
		try {
			message.setAttachments(null);
			Attachment attachment = new Attachment();
			List<Attachment> listAttachments = new ArrayList<Attachment>();
			attachment.setFileName("testFile.pdf");
			attachment.setContentType("application/pdf");

			byte[] input_file = Files.readAllBytes(Paths.get("src/test/testFile.pdf"));
			byte[] encodedBytes = Base64.getEncoder().encode(input_file);

			String pdfInBase64 = new String(encodedBytes);

			attachment.setContent(pdfInBase64);

			listAttachments.add(attachment);
			message.getContent().setPlainText(null);
			message.getContent().setHtmlText("text/html");
			message.setAttachments(listAttachments);
			SendMessageResponse response1 = email.sendMessage(message);
			assertNotNull(response1);
			assertNotNull(response1.getSourceTrackingId());
			assertNotNull(response1.getData());
			assertNull(response1.getErrors());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	
	@Test(expected=IOException.class)
	public void testSendMessageForNoRecipients() throws Exception {
		message.setRecipients(new String[]{});
		SendMessageResponse response = email.sendMessage(message);
		assertNotNull(response);
		assertNull(response.getSourceTrackingId());
		assertNull(response.getData());
		assertNotNull(response.getErrors());
		assertEquals(400, response.getErrors().get(0).getCode());
	}
	
	@Test(expected=IOException.class)
	public void testSendMessageForRecipientNull() throws Exception {
		message.setRecipients(null);
		SendMessageResponse response = email.sendMessage(message);
		assertNotNull(response);
		assertNull(response.getSourceTrackingId());
		assertNull(response.getData());
		assertNotNull(response.getErrors());
		assertEquals(400, response.getErrors().get(0).getCode());
	}
	
	@Test(expected=Exception.class)
	public void testSendMessageForContentNull() throws Exception {
		message.setContent(null);
		SendMessageResponse response = email.sendMessage(message);
	}
	
	@Test(expected=Exception.class)
	public void testSendMessageForHeader() throws Exception {
		message.setHeader(null);
		SendMessageResponse response = email.sendMessage(message);
	}	
	
	@Test
	public void testSendMessageForCCValue() {
		try {
			message.setCc(new String[] { "test_cc@test.com" });
			SendMessageResponse response = email.sendMessage(message);
			assertNotNull(response);
			assertNotNull(response.getSourceTrackingId());
			assertNotNull(response.getData());
			assertNull(response.getErrors());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	@Test
	public void testSendMessageForTrueForceSecureNotification() throws Exception {
		message.setForceSecureNotification("true");
		SendMessageResponse response = email.sendMessage(message);
		assertNotNull(response);
		assertNotNull(response.getSourceTrackingId());
		assertNotNull(response.getData());
		assertNull(response.getErrors());
	}
	
	@Test
	public void testSendMessageForFalseForceSecureNotification() throws Exception {
		message.setForceSecureNotification("FALSE");
		SendMessageResponse response = email.sendMessage(message);
		assertNotNull(response);
		assertNotNull(response.getSourceTrackingId());
		assertNotNull(response.getData());
		assertNull(response.getErrors());
	}
	
	@Test
	public void testSendMessageForNullForceSecureNotification() throws Exception {
		message.setForceSecureNotification(null);
		SendMessageResponse response = email.sendMessage(message);
		assertNotNull(response);
		assertNotNull(response.getSourceTrackingId());
		assertNotNull(response.getData());
		assertNull(response.getErrors());
	}	
	
	@Test
	public void testSendMessageForBase64Encoding() throws Exception {
		message.getContent().setPlainText(null);
		message.getContent().setHtmlText("<html><head></head><body><h1> Hello World! </h1> </body></html>");
		SendMessageResponse response = email.sendMessage(message);
		assertNotNull(response);
		assertNotNull(response.getSourceTrackingId());
		assertNotNull(response.getData());
		assertNull(response.getErrors());
	}
	
}
