package com.paubox.service;

import java.io.IOException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paubox.data.Form;
import com.paubox.data.FormSubmissionRequest;

public class FormsService implements FormsInterface {

    private static final String FORMS_BASE_URL = "https://apx.paubox.com/forms";

    public Form getForm(String formId) throws Exception {
        String url = FORMS_BASE_URL + "/public/form_data/" + formId;
        String responseStr = APIHelper.callToAPIByGet(url, null);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Form form = mapper.readValue(responseStr, Form.class);
        if (form.getId() == null) {
            throw new IOException(responseStr);
        }
        return form;
    }

    public void submitForm(String formId, FormSubmissionRequest submission) throws Exception {
        if (submission == null || submission.getFormData() == null) {
            throw new Exception("form_data cannot be null.");
        }
        String url = FORMS_BASE_URL + "/api/forms/" + formId + "/submissions";
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(submission);
        int statusCode = APIHelper.callToAPIByPostReturnCode(url, null, requestBody);
        if (statusCode == 404) {
            throw new Exception("Form not found.");
        } else if (statusCode == 400) {
            throw new Exception("Bad request: missing or invalid form_data.");
        } else if (statusCode != 201) {
            throw new Exception("Unexpected response code: " + statusCode);
        }
    }
}
