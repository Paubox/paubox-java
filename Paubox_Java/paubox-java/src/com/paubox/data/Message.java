package com.paubox.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Message {
	
	private String[] recipients ;
    private String[] bcc ;
    private String[] cc ;
    private Header header;
    private boolean allowNonTLS  = false;    
    private Content content ;
    private List<Attachment> attachments ;      
	/**
	 * @return the recipients
	 */
	public String[] getRecipients() {
		return recipients;
	}
	/**
	 * @param recipients the recipients to set
	 */
	public void setRecipients(String[] recipients) {
		this.recipients = recipients;
	}
	/**
	 * @return the bcc
	 */
	public String[] getBcc() {
		return bcc;
	}
	/**
	 * @param bcc the bcc to set
	 */
	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}
	/**
	 *  @return the cc
	 */
	public String[] getCc() {
		return cc;
	}
	/**
	 * 
	 * @param cc the cc to set
	 */
	public void setCc(String[] cc) {
		this.cc = cc;
	}
	/**
	 * @return the header
	 */
	public Header getHeader() {
		return header;
	}
	/**
	 * @param header the header to set
	 */
	public void setHeader(Header header) {
		this.header = header;
	}
	/**
	 * @return the allowNonTLS
	 */
	public boolean isAllowNonTLS() {
		return allowNonTLS;
	}
	/**
	 * @param allowNonTLS the allowNonTLS to set
	 */
	public void setAllowNonTLS(boolean allowNonTLS) {
		this.allowNonTLS = allowNonTLS;
	}	
	/**
	 * @return the content
	 */
	public Content getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(Content content) {
		this.content = content;
	}
	/**
	 * @return the attachments
	 */
	public List<Attachment> getAttachments() {
		return attachments;
	}
	/**
	 * @param attachments the attachments to set
	 */
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}		      
	
}
