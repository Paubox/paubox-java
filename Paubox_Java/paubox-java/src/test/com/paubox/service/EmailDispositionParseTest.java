package com.paubox.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;

import com.paubox.data.GetEmailDispositionResponse;
import com.paubox.data.MessageDeliveries;

// Shape of pb_email_api `receipt` / `receipt_delivery_status`: openedStatus is "opened" or null.
public class EmailDispositionParseTest {

	private static final String RECEIPT = "{\"sourceTrackingId\":\"6e1cf9a4-7bde-4834-8200-ed424b50c8a7\","
			+ "\"data\":{\"message\":{\"id\":\"<6e1cf9a4@example.com>\",\"message_deliveries\":["
			+ "{\"recipient\":\"opened@example.com\",\"status\":{\"deliveryStatus\":\"delivered\","
			+ "\"deliveryTime\":\"Tue, 13 Oct 2026 10:00:00 +0000\",\"openedStatus\":\"opened\","
			+ "\"openedTime\":\"Tue, 13 Oct 2026 10:05:00 +0000\"}},"
			+ "{\"recipient\":\"unopened@example.com\",\"status\":{\"deliveryStatus\":\"delivered\","
			+ "\"deliveryTime\":\"Tue, 13 Oct 2026 10:00:00 +0000\",\"openedStatus\":null,\"openedTime\":null}}"
			+ "]}}}";

	@Test
	public void keepsServerOpenedStatus() throws Exception {
		GetEmailDispositionResponse response = EmailService.parseEmailDisposition(RECEIPT);
		List<MessageDeliveries> deliveries = response.getData().getMessage().getMessage_deliveries();
		assertEquals("opened", deliveries.get(0).getStatus().getOpenedStatus());
		assertNull(deliveries.get(1).getStatus().getOpenedStatus());
	}
}
