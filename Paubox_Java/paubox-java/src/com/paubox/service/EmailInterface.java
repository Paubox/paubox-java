package com.paubox.service;

import com.paubox.data.CancelScheduledResponse;
import com.paubox.data.GetEmailDispositionResponse;
import com.paubox.data.Message;
import com.paubox.data.RescheduleResponse;
import com.paubox.data.ScheduleMessageResponse;
import com.paubox.data.ScheduledMessageStatus;
import com.paubox.data.SendMessageResponse;

public interface EmailInterface {

	public GetEmailDispositionResponse getEmailDisposition(String sourceTrackingId) throws Exception;

	public SendMessageResponse sendMessage(Message message) throws Exception;

	public ScheduleMessageResponse scheduleMessage(Message message, String scheduledAt) throws Exception;

	public ScheduledMessageStatus getScheduledMessage(String sourceTrackingId) throws Exception;

	public RescheduleResponse rescheduleMessage(String sourceTrackingId, String scheduledAt) throws Exception;

	public CancelScheduledResponse cancelScheduledMessage(String sourceTrackingId) throws Exception;

}
