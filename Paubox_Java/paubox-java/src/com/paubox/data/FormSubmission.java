package com.paubox.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FormSubmission {

    private String id;

    @JsonProperty("form_id")
    private String formId;

    @JsonProperty("form_data")
    private String formData;

    @JsonProperty("storage_type")
    private String storageType;

    @JsonProperty("storage_url")
    private String storageUrl;

    @JsonProperty("submitter_email")
    private String submitterEmail;

    private String recipients;

    private String attachment;

    @JsonProperty("attachment_name")
    private String attachmentName;

    @JsonProperty("attachment_url")
    private String attachmentUrl;

    @JsonProperty("attachment_type")
    private String attachmentType;

    @JsonProperty("created_at")
    private String createdAt;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFormId() { return formId; }
    public void setFormId(String formId) { this.formId = formId; }

    public String getFormData() { return formData; }
    public void setFormData(String formData) { this.formData = formData; }

    public String getStorageType() { return storageType; }
    public void setStorageType(String storageType) { this.storageType = storageType; }

    public String getStorageUrl() { return storageUrl; }
    public void setStorageUrl(String storageUrl) { this.storageUrl = storageUrl; }

    public String getSubmitterEmail() { return submitterEmail; }
    public void setSubmitterEmail(String submitterEmail) { this.submitterEmail = submitterEmail; }

    public String getRecipients() { return recipients; }
    public void setRecipients(String recipients) { this.recipients = recipients; }

    public String getAttachment() { return attachment; }
    public void setAttachment(String attachment) { this.attachment = attachment; }

    public String getAttachmentName() { return attachmentName; }
    public void setAttachmentName(String attachmentName) { this.attachmentName = attachmentName; }

    public String getAttachmentUrl() { return attachmentUrl; }
    public void setAttachmentUrl(String attachmentUrl) { this.attachmentUrl = attachmentUrl; }

    public String getAttachmentType() { return attachmentType; }
    public void setAttachmentType(String attachmentType) { this.attachmentType = attachmentType; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "FormSubmission [id=" + id + ", formId=" + formId
                + ", submitterEmail=" + submitterEmail + ", createdAt=" + createdAt + "]";
    }
}
