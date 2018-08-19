package com.paubox.test;

import java.util.ArrayList;
import java.util.List;

import com.paubox.config.ConfigurationManager;
import com.paubox.data.Attachment;
import com.paubox.data.Content;
import com.paubox.data.GetEmailDispositionResponse;
import com.paubox.data.Header;
import com.paubox.data.Message;
import com.paubox.data.SendMessageResponse;
import com.paubox.service.EmailInterface;
import com.paubox.service.EmailService;

public class PauboxClient {
	
	static Message getMessage()
    {
        Message message = new Message();
        message.setRecipients(new String[] { "username@domain.com" });

        Content content = new Content();
        Header header = new Header();
        Attachment attachment = new Attachment();
        List<Attachment> listAttachments = new ArrayList<Attachment>();
        attachment.setFileName("hello_world.txt");
        attachment.setContentType("text/html");
        attachment.setContent("SGVsbG8gV29ybGQh\n");

        listAttachments.add(attachment);


        header.setSubject("Test Mail from java");
        header.setFrom("renee@undefeatedgames.com");
        content.setPlainText("Hello Again");

        message.setHeader(header);
        message.setContent(content);
        message.setAttachments(listAttachments);
        
        return message;

    }
	
	public static void main(String[] args) {
		ConfigurationManager.getProperties("D:\\Projects\\paubox-java\\paubox-java\\Paubox_Java\\PauboxTest\\src\\resources\\config.properties");
		EmailInterface email = new EmailService();
		try {								
			SendMessageResponse responseMsg = email.sendMessage(getMessage());
			System.out.println("SourceTrackingId:" + responseMsg.getSourceTrackingId());
			GetEmailDispositionResponse response = email.getEmailDisposition(responseMsg.getSourceTrackingId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
