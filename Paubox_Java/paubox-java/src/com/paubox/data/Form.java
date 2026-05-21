package com.paubox.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Form {

    private String id;
    private String title;
    private String description;

    @JsonProperty("form_html")
    private String formHtml;

    @JsonProperty("form_json")
    private Object formJson;

    @JsonProperty("form_css")
    private String formCss;

    @JsonProperty("vanity_url")
    private String vanityUrl;

    private int version;
    private boolean active;

    @JsonProperty("customer_id")
    private int customerId;

    private boolean signable;

    @JsonProperty("signature_confirmation_label")
    private String signatureConfirmationLabel;

    @JsonProperty("submission_count")
    private int submissionCount;

    private String type;
    private boolean deleted;
    private boolean archived;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFormHtml() { return formHtml; }
    public void setFormHtml(String formHtml) { this.formHtml = formHtml; }

    public Object getFormJson() { return formJson; }
    public void setFormJson(Object formJson) { this.formJson = formJson; }

    public String getFormCss() { return formCss; }
    public void setFormCss(String formCss) { this.formCss = formCss; }

    public String getVanityUrl() { return vanityUrl; }
    public void setVanityUrl(String vanityUrl) { this.vanityUrl = vanityUrl; }

    public int getVersion() { return version; }
    public void setVersion(int version) { this.version = version; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public boolean isSignable() { return signable; }
    public void setSignable(boolean signable) { this.signable = signable; }

    public String getSignatureConfirmationLabel() { return signatureConfirmationLabel; }
    public void setSignatureConfirmationLabel(String signatureConfirmationLabel) {
        this.signatureConfirmationLabel = signatureConfirmationLabel;
    }

    public int getSubmissionCount() { return submissionCount; }
    public void setSubmissionCount(int submissionCount) { this.submissionCount = submissionCount; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public boolean isArchived() { return archived; }
    public void setArchived(boolean archived) { this.archived = archived; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Form [id=" + id + ", title=" + title + ", active=" + active
                + ", submissionCount=" + submissionCount + ", createdAt=" + createdAt + "]";
    }
}
