package test;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import com.paubox.config.ConfigarationManeger;
import com.paubox.data.GetEmailDispositionResponse;
import com.paubox.service.EmailInterface;
import com.paubox.service.EmailService;

public class TestEmailService {
	EmailInterface email = new EmailService();
	
	@BeforeClass
	public static void  init(){
		ConfigarationManeger.getProperties("D:/Projects/paubox-java/paubox-java/Paubox_Java/PauboxTest/src/resources/config.properties");
	}
	
	@Test
	public void testGetEmailDispositionForSuccess() {
		try {
			GetEmailDispositionResponse response = email.GetEmailDisposition("97b18032-59d5-47c7-a7c6-a2ed27f0f44e");
			assertNotNull(response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test(expected=Exception.class)
	public void testGetEmailDispositionForTrckingID() throws Exception {
			GetEmailDispositionResponse response = email.GetEmailDisposition(null);
	}
	
	@Test(expected=Exception.class)
	public void testGetEmailDispositionForinvalidID() throws Exception {
			GetEmailDispositionResponse response = email.GetEmailDisposition("garbagevalue");
	}

}
