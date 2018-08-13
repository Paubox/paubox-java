/**
 * 
 */
package test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import com.paubox.service.APIHelper;

import junit.framework.TestCase;

public class TestAPIHelper extends TestCase {

	/**
	 * Test method for {@link com.paubox.service.APIHelper#callToAPIByGet(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testCallToAPIByGetForSuccess() {
		String url="https://api.paubox.net/v1/undefeatedgames/message_receipt?sourceTrackingId=97b18032-59d5-47c7-a7c6-a2ed27f0f44e";
		String authToken="Token token=6f7c0110a47f82e7bff933f68cc8d7ec";
		try {
			APIHelper.callToAPIByGet(url, authToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected=Exception.class)
	public void testCallToAPIByGetForAuth()  {
		String url="https://api.paubox.net/v1/undefeatedgames/message_receipt?sourceTrackingId=97b18032-59d5-47c7-a7c6-a2ed27f0f44e";
		// token is wrong. it gives 401 error code
		String authToken="Token token=6f7c0110a47f82e7bff933f68cc8d7ec00";
		try {
			APIHelper.callToAPIByGet(url, authToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected=ClientProtocolException.class)
	public void testCallToAPIByGetForUrl()  {
		// url is mallformed. 
		String url="https:api.paubox.net/v1/undefeatedgames/message_receipt?sourceTrackingId=97b18032-59d5-47c7-a7c6-a2ed27f0f44e";
		String authToken="Token token=6f7c0110a47f82e7bff933f68cc8d7ec";
		try {
			APIHelper.callToAPIByGet(url, authToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
