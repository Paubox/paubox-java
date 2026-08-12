package com.paubox.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CopyFormRequest {

    @JsonProperty("form_id")
    private String formId;

    private String title;

    public CopyFormRequest() {
    }

    public CopyFormRequest(String formId, String title) {
        this.formId = formId;
        this.title = title;
    }

    public String getFormId() { return formId; }
    public void setFormId(String formId) { this.formId = formId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
