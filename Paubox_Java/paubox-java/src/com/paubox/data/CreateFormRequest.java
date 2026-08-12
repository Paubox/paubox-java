package com.paubox.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateFormRequest {

    private String title;
    private String description;

    @JsonProperty("form_html")
    private String formHtml;

    @JsonProperty("form_json")
    private Object formJson;

    @JsonProperty("form_css")
    private String formCss;

    @JsonProperty("customer_id")
    private Integer customerId;

    private String recipient;
    private Boolean signable;

    @JsonProperty("signature_confirmation_label")
    private String signatureConfirmationLabel;

    @JsonProperty("subscription_list_id")
    private String subscriptionListId;

    private String type;
    private Boolean active;
    private Integer version;

    @JsonProperty("submission_count")
    private Integer submissionCount;

    public CreateFormRequest() {
    }

    public CreateFormRequest(String title, Object formJson, Integer customerId, Integer version) {
        this.title = title;
        this.formJson = formJson;
        this.customerId = customerId;
        this.version = version;
    }

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

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }

    public Boolean getSignable() { return signable; }
    public void setSignable(Boolean signable) { this.signable = signable; }

    public String getSignatureConfirmationLabel() { return signatureConfirmationLabel; }
    public void setSignatureConfirmationLabel(String signatureConfirmationLabel) {
        this.signatureConfirmationLabel = signatureConfirmationLabel;
    }

    public String getSubscriptionListId() { return subscriptionListId; }
    public void setSubscriptionListId(String subscriptionListId) { this.subscriptionListId = subscriptionListId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }

    public Integer getSubmissionCount() { return submissionCount; }
    public void setSubmissionCount(Integer submissionCount) { this.submissionCount = submissionCount; }
}
