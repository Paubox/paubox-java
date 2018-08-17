package com.paubox.test;

import java.util.ArrayList;
import java.util.List;

import com.paubox.config.ConfigarationManeger;
import com.paubox.data.Attachment;
import com.paubox.data.Content;
import com.paubox.data.Header;
import com.paubox.data.Message;
import com.paubox.data.SendMessageResponse;
import com.paubox.service.EmailInterface;
import com.paubox.service.EmailService;

public class PauboxClient {
	
	static Message getMessage()
    {
        Message message = new Message();
        message.setRecipients(new String[] { "vighneshtrivedi2004@gmail.com" });

        Content content = new Content();
        Header header = new Header();
        Attachment attachment = new Attachment();
        List<Attachment> listAttachments = new ArrayList<Attachment>();
        attachment.setFileName("hello_world.txt");
        attachment.setContentType("text/plain");
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
		ConfigarationManeger.getProperties("E:\\WORK\\GIT\\paubox-java\\Paubox_Java\\PauboxTest\\src\\resources\\config.properties");
		EmailInterface email = new EmailService();
		try {
			/*GetEmailDispositionResponse response = email.GetEmailDisposition("97b18032-59d5-47c7-a7c6-a2ed27f0f44e");
			System.out.println("sysout:" + response.toString());*/
			
			SendMessageResponse responseMsg = email.sendMessage(getMessage());
			System.out.println("SourceTrackingId:" + responseMsg.getSourceTrackingId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
