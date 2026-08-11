package com.paubox.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response for form update. Also reused for archive/unarchive responses,
 * where formId stays null.
 */
public class UpdateFormResponse {

    private String detail;

    @JsonProperty("form_id")
    private String formId;

    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }

    public String getFormId() { return formId; }
    public void setFormId(String formId) { this.formId = formId; }
}
