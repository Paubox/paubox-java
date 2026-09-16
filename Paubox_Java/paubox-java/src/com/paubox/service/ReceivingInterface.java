package com.paubox.service;

public interface ReceivingInterface {

	public String listReceivingDomains() throws Exception;

	public String createReceivingDomain(String slug) throws Exception;

	public String getReceivingDomain(int domainId) throws Exception;

	public String deleteReceivingDomain(int domainId) throws Exception;

	public String listReceivingMailboxes(int domainId) throws Exception;

	public String createReceivingMailbox(int domainId, String name, String password, Long quotaBytes) throws Exception;

	public String getReceivingMailbox(int domainId, int mailboxId) throws Exception;

	public String deleteReceivingMailbox(int domainId, int mailboxId) throws Exception;

	public String listReceivedEmails(Integer limit, String after, String before) throws Exception;

	public String getReceivedEmail(String emailId) throws Exception;

	public byte[] getReceivedEmailAttachment(String emailId, String blobId) throws Exception;

}
