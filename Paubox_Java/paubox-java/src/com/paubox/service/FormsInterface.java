package com.paubox.service;

import com.paubox.data.Form;
import com.paubox.data.FormSubmissionRequest;

public interface FormsInterface {

    /**
     * @param formId UUID of the form to retrieve
     * @return Form
     * @throws Exception
     */
    public Form getForm(String formId) throws Exception;

    /**
     * @param formId UUID of the form being submitted
     * @param submission FormSubmissionRequest containing form_data and optional attachments
     * @throws Exception
     */
    public void submitForm(String formId, FormSubmissionRequest submission) throws Exception;
}
