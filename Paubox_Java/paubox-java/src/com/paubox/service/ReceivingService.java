package com.paubox.service;

import java.net.URLEncoder;
import com.paubox.common.Constants;
import org.json.simple.JSONObject;

public class ReceivingService implements ReceivingInterface {

	private String baseApiUrl = "https://api.paubox.net/v1/" + Constants.API_USER + "/";

	public String listReceivingDomains() throws Exception {
		String url = baseApiUrl + "receiving/domains";
		return APIHelper.callToAPIByGet(url, getAuthorizationHeader());
	}

	public String createReceivingDomain(String slug) throws Exception {
		String url = baseApiUrl + "receiving/domains";
		JSONObject requestJSON = new JSONObject();
		if (slug != null) {
			requestJSON.put("slug", slug);
		}
		return APIHelper.callToAPIByPost(url, getAuthorizationHeader(), requestJSON.toJSONString());
	}

	public String getReceivingDomain(int domainId) throws Exception {
		String url = baseApiUrl + "receiving/domains/" + domainId;
		return APIHelper.callToAPIByGet(url, getAuthorizationHeader());
	}

	public String deleteReceivingDomain(int domainId) throws Exception {
		String url = baseApiUrl + "receiving/domains/" + domainId;
		return APIHelper.callToAPIByDelete(url, getAuthorizationHeader());
	}

	public String listReceivingMailboxes(int domainId) throws Exception {
		String url = baseApiUrl + "receiving/domains/" + domainId + "/mailboxes";
		return APIHelper.callToAPIByGet(url, getAuthorizationHeader());
	}

	public String createReceivingMailbox(int domainId, String name, String password, Long quotaBytes) throws Exception {
		if (name == null || name.isEmpty()) {
			throw new Exception("name cannot be null or empty.");
		}
		if (password == null || password.isEmpty()) {
			throw new Exception("password cannot be null or empty.");
		}
		String url = baseApiUrl + "receiving/domains/" + domainId + "/mailboxes";
		JSONObject requestJSON = new JSONObject();
		requestJSON.put("name", name);
		requestJSON.put("password", password);
		if (quotaBytes != null) {
			requestJSON.put("quota_bytes", quotaBytes);
		}
		return APIHelper.callToAPIByPost(url, getAuthorizationHeader(), requestJSON.toJSONString());
	}

	public String getReceivingMailbox(int domainId, int mailboxId) throws Exception {
		String url = baseApiUrl + "receiving/domains/" + domainId + "/mailboxes/" + mailboxId;
		return APIHelper.callToAPIByGet(url, getAuthorizationHeader());
	}

	public String deleteReceivingMailbox(int domainId, int mailboxId) throws Exception {
		String url = baseApiUrl + "receiving/domains/" + domainId + "/mailboxes/" + mailboxId;
		return APIHelper.callToAPIByDelete(url, getAuthorizationHeader());
	}

	public String listReceivedEmails(Integer limit, String after, String before) throws Exception {
		StringBuilder queryString = new StringBuilder();
		appendParam(queryString, "limit", limit);
		appendParam(queryString, "after", after);
		appendParam(queryString, "before", before);
		String url = baseApiUrl + "receiving" + queryString.toString();
		return APIHelper.callToAPIByGet(url, getAuthorizationHeader());
	}

	public String getReceivedEmail(String emailId) throws Exception {
		if (emailId == null || emailId.isEmpty()) {
			throw new Exception("emailId cannot be null or empty.");
		}
		String url = baseApiUrl + "receiving/" + emailId;
		return APIHelper.callToAPIByGet(url, getAuthorizationHeader());
	}

	public byte[] getReceivedEmailAttachment(String emailId, String blobId) throws Exception {
		if (emailId == null || emailId.isEmpty()) {
			throw new Exception("emailId cannot be null or empty.");
		}
		if (blobId == null || blobId.isEmpty()) {
			throw new Exception("blobId cannot be null or empty.");
		}
		String url = baseApiUrl + "receiving/" + emailId + "/attachments/" + blobId;
		return APIHelper.callToAPIByGetBytes(url, getAuthorizationHeader());
	}

	private static String getAuthorizationHeader() {
		return "Token token=" + Constants.API_KEY;
	}

	private static void appendParam(StringBuilder queryString, String name, Object value) throws Exception {
		if (value == null) {
			return;
		}
		queryString.append(queryString.length() == 0 ? "?" : "&");
		queryString.append(name).append("=").append(URLEncoder.encode(String.valueOf(value), "UTF-8"));
	}

}
