import paubox-java.src.com.paubox.config.ConfigarationManeger;
import paubox-java.src.com.paubox.data.GetEmailDispositionResponse;
import paubox-java.src.com.paubox.service.EmailInterface;
import paubox-java.src.com.paubox.service.EmailService;

public class PauboxClient {
	
	public static void main(String[] args) {
		ConfigarationManeger.getProperties("D:/Projects/paubox-java/paubox-java/Paubox_Java/PauboxTest/src/resources/config.properties");
		EmailInterface email = new EmailService();
		try {
			GetEmailDispositionResponse response = email.GetEmailDisposition("97b18032-59d5-47c7-a7c6-a2ed27f0f44e");
			System.out.println("sysout:" + response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
