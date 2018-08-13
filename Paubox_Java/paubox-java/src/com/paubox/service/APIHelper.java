package com.paubox.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.paubox.common.Constants;

public class APIHelper {
	
	public static String callToAPIByGet(String baseAPIUrl, String authHeader) throws Exception {
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			
			HttpGet getRequest = new HttpGet(baseAPIUrl);
			
			getRequest.addHeader("accept", "application/json");
			
			if(null!=authHeader){
				getRequest.addHeader("Authorization", authHeader);
			}

			HttpResponse response = httpClient.execute(getRequest);

			if (Constants.HTTP_STATUS_SUCCESS != response.getStatusLine().getStatusCode()) {
				throw new Exception("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			BufferedReader rd = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));

				StringBuffer result = new StringBuffer();
				String line = "";
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
				
			return result.toString();

		} catch (ClientProtocolException e) {

			throw new Exception(e);

		} catch (IOException e) {

			throw new Exception(e);
		}
	}

	public  static String callToAPIByPost(String BaseAPIUrl, String authHeader, String requestBody) {
		 try {

				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost postRequest = new HttpPost(BaseAPIUrl);

				StringEntity input = new StringEntity(requestBody);
				input.setContentType("application/json");
				postRequest.setEntity(input);

				HttpResponse response = httpClient.execute(postRequest);

				if (Constants.HTTP_STATUS_SUCCESS != response.getStatusLine().getStatusCode()) {
					throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				}
				
				return response.getEntity().getContent().toString();

			  } catch (MalformedURLException e) {

				e.printStackTrace();
			
			  } catch (IOException e) {

				e.printStackTrace();

			  }

		 	return null;
	}

}
