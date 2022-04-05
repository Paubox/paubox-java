/**
 * 
 */
package test;

import com.paubox.config.ConfigurationManager;
import org.apache.http.client.ClientProtocolException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.paubox.service.APIHelper;

import junit.framework.TestCase;

public class TestAPIHelper extends TestCase {

	String baseUrl;
	String authToken;


	/**
	 * Test method for
	 * {@link com.paubox.service.APIHelper#callToAPIByGet(java.lang.String, java.lang.String)}.
	 */

	public void setParams() {
		String propFile = System.getProperty("properties");
		if (propFile == null || propFile.equals(""))
		{
			propFile = "src/test/config.properties";
		}
		ConfigurationManager.getProperties(propFile);
		baseUrl = ConfigurationManager.getProperty("BASE_URL") + "/" + ConfigurationManager.getProperty("APIUSER") + "/";
		authToken = "Token token=" + ConfigurationManager.getProperty("APIKEY");
	}


	@Test
	public void testCallToAPIByGetForSuccess() {
		setParams();
		String url = baseUrl + "/message_receipt?sourceTrackingId=97b18032-59d5-47c7-a7c6-a2ed27f0f44e";
		try {
			String response =  APIHelper.callToAPIByGet(url, authToken);
			assertNotNull(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(expected = Exception.class)
	public void testCallToAPIByGetForAuth() {
		setParams();
		String url = baseUrl + "/message_receipt?sourceTrackingId=97b18032-59d5-47c7-a7c6-a2ed27f0f44e";
		try {
			String response =  APIHelper.callToAPIByGet(url, "Token token=b00000964da30b000b7e00000e27a1b000000000");
			assertNotNull(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(expected = ClientProtocolException.class)
	public void testCallToAPIByGetForUrl() {
		// url is mallformed.
		setParams();
		String url = baseUrl + "/message_receipt?sourceTrackingId=97b18032-59d5-47c7-a7c6-a2ed27f0f44e";
		url = url.replaceFirst("//", "");
		try {
			String response =  APIHelper.callToAPIByGet(url, authToken);
			assertNotNull(response);
		} catch (ClientProtocolException e) {
//			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCallToAPIByPostForSuccess() {
		setParams();
		String url = baseUrl + "/messages";
		String requestBody = "{\"data\":{\"message\":{\"headers\":{\"subject\":\"Test Mail from java\",\"from\":\"renee@undefeatedgames.com\",\"reply-to\":null},\"bcc\":null,\"attachments\":[{\"fileName\":\"hello_world.txt\",\"contentType\":\"text/plain\",\"content\":\"SGVsbG8gV29ybGQh\\n\"}],\"allowNonTLS\":false,\"recipients\":[\"username@domain.com\"],\"content\":{\"text/html\":null,\"text/plain\":\"Hello Again\"}}}}";
		try {
			String response = APIHelper.callToAPIByPost(url, authToken, requestBody);
			assertNotNull(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(expected = Exception.class)
	public void testCallToAPIByPostForAuth() {
		setParams();
		String url = baseUrl + "/messages";
		// token is wrong. it gives 401 error code
		String requestBody = "{\"data\":{\"message\":{\"headers\":{\"subject\":\"Test Mail from java\",\"from\":\"renee@undefeatedgames.com\",\"reply-to\":null},\"bcc\":null,\"attachments\":[{\"fileName\":\"hello_world.txt\",\"contentType\":\"text/plain\",\"content\":\"SGVsbG8gV29ybGQh\\n\"}],\"allowNonTLS\":false,\"recipients\":[\"username@domain.com\"],\"content\":{\"text/html\":null,\"text/plain\":\"Hello Again\"}}}}";
		try {
			String response = APIHelper.callToAPIByPost(url, "Token token=b00000964da30b000b7e00000e27a1b000000000", requestBody);
			assertNotNull(response);
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	@Test(expected = ClientProtocolException.class)
	public void testCallToAPIByPostForUrl() {
		// url is mallformed.
		setParams();
		String url = baseUrl + "/messages";
		url = url.replaceFirst("//", "");
		String requestBody = "{\"data\":{\"message\":{\"headers\":{\"subject\":\"Test Mail from java\",\"from\":\"renee@undefeatedgames.com\",\"reply-to\":null},\"bcc\":null,\"attachments\":[{\"fileName\":\"hello_world.txt\",\"contentType\":\"text/plain\",\"content\":\"SGVsbG8gV29ybGQh\\n\"}],\"allowNonTLS\":false,\"recipients\":[\"username@domain.com\"],\"content\":{\"text/html\":null,\"text/plain\":\"Hello Again\"}}}}";
		try {
			 APIHelper.callToAPIByPost(url, authToken, requestBody);
		} catch(ClientProtocolException cpe) {
			// do nothing
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCallToAPIByPostForOptionalValues() {
		// removing the optional values in messsage.
		setParams();
		String url = baseUrl + "/messages";
		String requestBody = "{\"data\":{\"message\":{\"headers\":{\"subject\":\"Test Mail from java\",\"from\":\"renee@undefeatedgames.com\",\"reply-to\":null},\"bcc\":null,\"attachments\":[{\"fileName\":\"hello_world.txt\",\"contentType\":\"text/plain\",\"content\":\"SGVsbG8gV29ybGQh\\n\"}],\"recipients\":[\"username@domain.com\"],\"content\":{\"text/html\":null,\"text/plain\":\"Hello Again\"}}}}";
		try {
			String response = APIHelper.callToAPIByPost(url, authToken, requestBody);
			assertNotNull(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCallToAPIByPostForContent() {
		// if content type is text plain , and text plain field is null ? 
		setParams();
		String url = baseUrl + "/messages";
		String requestBody = "{\"data\":{\"message\":{\"headers\":{\"subject\":\"Test Mail from java\",\"from\":\"renee@undefeatedgames.com\",\"reply-to\":null},\"bcc\":null,\"attachments\":[{\"fileName\":\"hello_world.txt\",\"contentType\":\"text/plain\",\"content\":\"SGVsbG8gV29ybGQh\\n\"}],\"allowNonTLS\":false,\"recipients\":[\"username@domain.com\"],\"content\":{\"text/html\":null,\"text/plain\":null}}}}";
		try {
			String response = APIHelper.callToAPIByPost(url, authToken, requestBody);
			assertNotNull(response);
			
			 //if content type is text html , and text html field is null 
			requestBody= "{\"data\":{\"message\":{\"headers\":{\"subject\":\"Test Mail from java\",\"from\":\"renee@undefeatedgames.com\",\"reply-to\":null},\"bcc\":null,\"attachments\":[{\"fileName\":\"hello_world.txt\",\"contentType\":\"text/html\",\"content\":\"SGVsbG8gV29ybGQh\\n\"}],\"allowNonTLS\":false,\"recipients\":[\"username@domain.com\"],\"content\":{\"text/html\":null,\"text/plain\":\"Hello Again\"}}}}";
			String responseHtml = APIHelper.callToAPIByPost(url, authToken, requestBody);
			assertNotNull(responseHtml);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCallToAPIByPostForAttachment() {
		//  if attachment.contentType is not valid MIME type
		setParams();
		String url = baseUrl + "/messages";
		String requestBody= "{\"data\":{\"message\":{\"headers\":{\"subject\":\"Test Mail from java\",\"from\":\"renee@undefeatedgames.com\",\"reply-to\":null},\"bcc\":null,\"attachments\":[{\"fileName\":\"hello_world.txt\",\"contentType\":\"text11/html11\",\"content\":\"SGVsbG8gV29ybGQh\\n\"}],\"allowNonTLS\":false,\"recipients\":[\"username@domain.com\"],\"content\":{\"text/html\":null,\"text/plain\":\"Hello Again\"}}}}";
		try {
			String response = APIHelper.callToAPIByPost(url, authToken, requestBody);
			assertNotNull(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCallToAPIByPostForMandatory () {
		// content is missing
		setParams();
		String url = baseUrl + "/messages";
		//missing header
		String requestBody = "{\"data\":{\"message\":{\"bcc\":null,\"attachments\":[{\"fileName\":\"hello_world.txt\",\"contentType\":\"text/plain\",\"content\":\"SGVsbG8gV29ybGQh\\n\"}],\"allowNonTLS\":false,\"recipients\":[\"username@domain.com\"],\"content\":{\"text/html\":null,\"text/plain\":\"Hello Again\"}}}}";
		try {
			String response=APIHelper.callToAPIByPost(url, authToken, requestBody);
			assertNotNull(response);
			
			//content is missing
			requestBody = "{\"data\":{\"message\":{\"headers\":{\"subject\":\"Test Mail from java\",\"from\":\"renee@undefeatedgames.com\",\"reply-to\":null},\"bcc\":null,\"attachments\":[{\"fileName\":\"hello_world.txt\",\"contentType\":\"text/plain\",\"content\":\"SGVsbG8gV29ybGQh\\n\"}],\"allowNonTLS\":false,\"recipients\":[\"username@domain.com\"]}}}";
			String responseContent=APIHelper.callToAPIByPost(url, authToken, requestBody);
			assertNotNull(responseContent);
			
			//recipients is missing
			requestBody = "{\"data\":{\"message\":{\"headers\":{\"subject\":\"Test Mail from java\",\"from\":\"renee@undefeatedgames.com\",\"reply-to\":null},\"bcc\":null,\"attachments\":[{\"fileName\":\"hello_world.txt\",\"contentType\":\"text/plain\",\"content\":\"SGVsbG8gV29ybGQh\\n\"}],\"allowNonTLS\":false,\"content\":{\"text/html\":null,\"text/plain\":\"Hello Again\"}}}}";
			String responseRecipients=APIHelper.callToAPIByPost(url, authToken, requestBody);
			assertNotNull(responseRecipients);
			
			//invalid mail id
			requestBody = "{\"data\":{\"message\":{\"headers\":{\"subject\":\"Test Mail from java\",\"from\":\"renee@undefeatedgames1.com\",\"reply-to\":null},\"bcc\":null,\"attachments\":[{\"fileName\":\"hello_world.txt\",\"contentType\":\"text/plain\",\"content\":\"SGVsbG8gV29ybGQh\\n\"}],\"recipients\":[\"username@domain.com\"],\"content\":{\"text/html\":null,\"text/plain\":\"Hello Again\"}}}}";
			String responseMail=APIHelper.callToAPIByPost(url, authToken, requestBody);
			assertNotNull(responseMail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
