/**
 * 
 */
package test;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import com.paubox.service.APIHelper;

import junit.framework.TestCase;

public class TestAPIHelper extends TestCase {

	/**
	 * Test method for
	 * {@link com.paubox.service.APIHelper#callToAPIByGet(java.lang.String, java.lang.String)}.
	 */

	@Test
	public void testCallToAPIByGetForSuccess() {
		String url = "https://api.paubox.net/v1/undefeatedgames/message_receipt?sourceTrackingId=97b18032-59d5-47c7-a7c6-a2ed27f0f44e";
		String authToken = "Token token=6f7c0110a47f82e7bff933f68cc8d7ec";
		try {
			String response =  APIHelper.callToAPIByGet(url, authToken);
			assertNotNull(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(expected = Exception.class)
	public void testCallToAPIByGetForAuth() {
		String url = "https://api.paubox.net/v1/undefeatedgames/message_receipt?sourceTrackingId=97b18032-59d5-47c7-a7c6-a2ed27f0f44e";
		// token is wrong. it gives 401 error code
		String authToken = "Token token=6f7c0110a47f82e7bff933f68cc8d7ec00";
		try {
			String response =  APIHelper.callToAPIByGet(url, authToken);
			assertNotNull(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(expected = ClientProtocolException.class)
	public void testCallToAPIByGetForUrl() {
		// url is mallformed.
		String url = "https:api.paubox.net/v1/undefeatedgames/message_receipt?sourceTrackingId=97b18032-59d5-47c7-a7c6-a2ed27f0f44e";
		String authToken = "Token token=6f7c0110a47f82e7bff933f68cc8d7ec";
		try {
			String response =  APIHelper.callToAPIByGet(url, authToken);
			assertNotNull(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCallToAPIByPostForSuccess() {
		String url = "https://api.paubox.net/v1/undefeatedgames/messages";
		String authToken = "Token token=6f7c0110a47f82e7bff933f68cc8d7ec";
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
		String url = "https://api.paubox.net/v1/undefeatedgames/messages";
		// token is wrong. it gives 401 error code
		String authToken = "Token token=6f7c0110a47f82e7bff933f68cc8d7ec00";
		String requestBody = "{\"data\":{\"message\":{\"headers\":{\"subject\":\"Test Mail from java\",\"from\":\"renee@undefeatedgames.com\",\"reply-to\":null},\"bcc\":null,\"attachments\":[{\"fileName\":\"hello_world.txt\",\"contentType\":\"text/plain\",\"content\":\"SGVsbG8gV29ybGQh\\n\"}],\"allowNonTLS\":false,\"recipients\":[\"username@domain.com\"],\"content\":{\"text/html\":null,\"text/plain\":\"Hello Again\"}}}}";
		try {
			String response = APIHelper.callToAPIByPost(url, authToken, requestBody);
			assertNotNull(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(expected = ClientProtocolException.class)
	public void testCallToAPIByPostForUrl() {
		// url is mallformed.
		String url = "https:api.paubox.net/v1/undefeatedgames/messages";
		String authToken = "Token token=6f7c0110a47f82e7bff933f68cc8d7ec";
		String requestBody = "{\"data\":{\"message\":{\"headers\":{\"subject\":\"Test Mail from java\",\"from\":\"renee@undefeatedgames.com\",\"reply-to\":null},\"bcc\":null,\"attachments\":[{\"fileName\":\"hello_world.txt\",\"contentType\":\"text/plain\",\"content\":\"SGVsbG8gV29ybGQh\\n\"}],\"allowNonTLS\":false,\"recipients\":[\"username@domain.com\"],\"content\":{\"text/html\":null,\"text/plain\":\"Hello Again\"}}}}";
		try {
			 APIHelper.callToAPIByPost(url, authToken, requestBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCallToAPIByPostForOptionalValues() {
		// removing the optional values in messsage.
		String url = "https://api.paubox.net/v1/undefeatedgames/messages";
		String authToken = "Token token=6f7c0110a47f82e7bff933f68cc8d7ec";
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
		String url = "https://api.paubox.net/v1/undefeatedgames/messages";
		String authToken = "Token token=6f7c0110a47f82e7bff933f68cc8d7ec";
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
		String url = "https://api.paubox.net/v1/undefeatedgames/messages";
		String authToken = "Token token=6f7c0110a47f82e7bff933f68cc8d7ec";
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
		String url = "https://api.paubox.net/v1/undefeatedgames/messages";
		String authToken = "Token token=6f7c0110a47f82e7bff933f68cc8d7ec";
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
