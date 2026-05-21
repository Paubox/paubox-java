package com.paubox.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class FormSubmissionRequest {

    @JsonProperty("form_data")
    private Map<String, Object> formData;

    private List<FormSubmissionAttachment> attachments;

    public FormSubmissionRequest() {}

    public FormSubmissionRequest(Map<String, Object> formData) {
        this.formData = formData;
    }

    public Map<String, Object> getFormData() { return formData; }
    public void setFormData(Map<String, Object> formData) { this.formData = formData; }

    public List<FormSubmissionAttachment> getAttachments() { return attachments; }
    public void setAttachments(List<FormSubmissionAttachment> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "FormSubmissionRequest [formData=" + formData + ", attachments=" + attachments + "]";
    }
}
