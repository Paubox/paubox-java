package com.paubox.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

public class APIHelper {

	private static final int CONNECT_TIMEOUT_MS = 30000;
	private static final int SOCKET_TIMEOUT_MS = 30000;

	private static DefaultHttpClient newClient() {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECT_TIMEOUT_MS);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, SOCKET_TIMEOUT_MS);
		httpClient.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, Boolean.FALSE);
		return httpClient;
	}
	
	/**
	 * Call http get API
	 * @param baseAPIUrl String
	 * @param authHeader String
	 * @return String
	 * @throws Exception
	 */
	public static String callToAPIByGet(String baseAPIUrl, String authHeader) throws Exception {
		try {
			DefaultHttpClient httpClient = newClient();
			
			HttpGet getRequest = new HttpGet(baseAPIUrl);			
			getRequest.addHeader("accept", "application/json");
			
			if(null!=authHeader){
				getRequest.addHeader("Authorization", authHeader);
			}				

			HttpResponse response = httpClient.execute(getRequest);
			return processApiResponse(response);					
			
		} catch (ClientProtocolException e) {

			throw new Exception(e);

		} catch (IOException e) {

			throw new Exception(e);
		}
	}

	/**
	 * process the response of API 
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	private static String processApiResponse(HttpResponse response) throws Exception {
				
		BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		return result.toString();
	}
	
/**
 * Call http POST API
 * @param baseAPIUrl String
 * @param authHeader String
 * @param requestBody String
 * @return String
 * @throws Exception
 */
	public  static String callToAPIByPost(String baseAPIUrl, String authHeader, String requestBody) throws Exception {
		 try {

				DefaultHttpClient httpClient = newClient();
				HttpPost postRequest = new HttpPost(baseAPIUrl);

				StringEntity input = new StringEntity(requestBody);
				input.setContentType("application/json");
				postRequest.setEntity(input);
				
				if(null!=authHeader){
					postRequest.addHeader("Authorization", authHeader);
				}

				HttpResponse response = httpClient.execute(postRequest);
				
				return processApiResponse(response);

			  } catch (MalformedURLException e) {

				  throw new Exception(e);
			
			  } catch (IOException e) {

				  throw new Exception(e);

			  }

	}

/**
 * Call http POST API and return the HTTP status code.
 * Use when the response body may be empty (e.g. 201 No Content).
 * @param baseAPIUrl String
 * @param authHeader String
 * @param requestBody String
 * @return int HTTP status code
 * @throws Exception
 */
	public static int callToAPIByPostReturnCode(String baseAPIUrl, String authHeader, String requestBody) throws Exception {
		try {
			DefaultHttpClient httpClient = newClient();
			HttpPost postRequest = new HttpPost(baseAPIUrl);

			StringEntity input = new StringEntity(requestBody);
			input.setContentType("application/json");
			postRequest.setEntity(input);

			if (null != authHeader) {
				postRequest.addHeader("Authorization", authHeader);
			}

			HttpResponse response = httpClient.execute(postRequest);
			return response.getStatusLine().getStatusCode();

		} catch (MalformedURLException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		}
	}

/**
 * Call http PUT API
 * @param baseAPIUrl String
 * @param authHeader String
 * @param requestBody String
 * @return String
 * @throws Exception
 */
	public static String callToAPIByPut(String baseAPIUrl, String authHeader, String requestBody) throws Exception {
		try {

			DefaultHttpClient httpClient = newClient();
			HttpPut putRequest = new HttpPut(baseAPIUrl);

			StringEntity input = new StringEntity(requestBody);
			input.setContentType("application/json");
			putRequest.setEntity(input);

			if (null != authHeader) {
				putRequest.addHeader("Authorization", authHeader);
			}

			HttpResponse response = httpClient.execute(putRequest);

			return processApiResponse(response);

		} catch (MalformedURLException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		}
	}

/**
 * Call http GET API and return the raw response body bytes.
 * Use for binary responses (e.g. CSV or PDF downloads).
 * @param baseAPIUrl String
 * @param authHeader String
 * @return byte[] response body
 * @throws Exception
 */
	public static byte[] callToAPIByGetBytes(String baseAPIUrl, String authHeader) throws Exception {
		try {
			DefaultHttpClient httpClient = newClient();

			HttpGet getRequest = new HttpGet(baseAPIUrl);
			getRequest.addHeader("accept", "*/*");

			if (null != authHeader) {
				getRequest.addHeader("Authorization", authHeader);
			}

			HttpResponse response = httpClient.execute(getRequest);
			int code = response.getStatusLine().getStatusCode();
			if (code != 200) {
				throw new Exception("Unexpected response code: " + code);
			}
			return EntityUtils.toByteArray(response.getEntity());

		} catch (ClientProtocolException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		}
	}

}
