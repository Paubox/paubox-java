package com.paubox.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request body for updating a form. Update semantics are PATCH-like:
 * a null field is omitted from the request and the form's value stays unchanged.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateFormRequest {

    private String title;
    private String description;

    @JsonProperty("form_json")
    private Object formJson;

    @JsonProperty("vanity_url")
    private String vanityUrl;

    private String recipient;
    private Boolean active;

    @JsonProperty("subscription_list_id")
    private String subscriptionListId;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Object getFormJson() { return formJson; }
    public void setFormJson(Object formJson) { this.formJson = formJson; }

    public String getVanityUrl() { return vanityUrl; }
    public void setVanityUrl(String vanityUrl) { this.vanityUrl = vanityUrl; }

    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public String getSubscriptionListId() { return subscriptionListId; }
    public void setSubscriptionListId(String subscriptionListId) { this.subscriptionListId = subscriptionListId; }
}
