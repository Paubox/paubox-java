package com.paubox.service;

import com.paubox.data.Form;
import com.paubox.data.CreateFormRequest;
import com.paubox.data.FormListRequest;
import com.paubox.data.FormListResponse;
import com.paubox.data.FormStats;
import com.paubox.data.FormSubmissionListRequest;
import com.paubox.data.FormSubmissionListResponse;
import com.paubox.data.FormSubmissionRequest;
import com.paubox.data.UpdateFormRequest;
import com.paubox.data.UpdateFormResponse;

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

    /**
     * List forms. Requires a scoped API key with the forms scope.
     * When authenticating with a scoped API key, the query's customerId must be set to the
     * API key's customer id; omitting it results in a 403 Forbidden from the API.
     * @param query FormListRequest with optional filters and pagination; customerId is required
     *              when authenticating with a scoped API key
     * @return FormListResponse
     * @throws Exception
     */
    public FormListResponse listForms(FormListRequest query) throws Exception;

    /**
     * Create a form. Requires a scoped API key with the forms scope.
     * @param request CreateFormRequest with at least title, form_json, customer_id and version
     * @return String id of the new form
     * @throws Exception
     */
    public String createForm(CreateFormRequest request) throws Exception;

    /**
     * Retrieve a form regardless of active/archived state. Requires a scoped API key with the forms scope.
     * @param formId UUID of the form to retrieve
     * @return Form
     * @throws Exception
     */
    public Form getFormById(String formId) throws Exception;

    /**
     * Update a form; null fields in the request are left unchanged. Requires a scoped API key with the forms scope.
     * @param formId UUID of the form to update
     * @param request UpdateFormRequest with the fields to change
     * @return UpdateFormResponse
     * @throws Exception
     */
    public UpdateFormResponse updateForm(String formId, UpdateFormRequest request) throws Exception;

    /**
     * Archive a form. Requires a scoped API key with the forms scope.
     * @param formId UUID of the form to archive
     * @throws Exception
     */
    public void archiveForm(String formId) throws Exception;

    /**
     * Unarchive a form. Requires a scoped API key with the forms scope.
     * @param formId UUID of the form to unarchive
     * @throws Exception
     */
    public void unarchiveForm(String formId) throws Exception;

    /**
     * Copy an existing form under a new title. Requires a scoped API key with the forms scope.
     * @param formId UUID of the form to copy
     * @param newTitle title for the copied form
     * @return Form the newly created copy
     * @throws Exception
     */
    public Form copyForm(String formId, String newTitle) throws Exception;

    /**
     * Retrieve form statistics. Requires a scoped API key with the forms scope.
     * @param customerId optional customer id; may be null to use the API key's customer
     * @return FormStats
     * @throws Exception
     */
    public FormStats getFormStats(Integer customerId) throws Exception;

    /**
     * List submissions for a form. Requires a scoped API key with the forms scope.
     * @param formId UUID of the form
     * @param query FormSubmissionListRequest with optional filters and pagination; may be null for all defaults
     * @return FormSubmissionListResponse
     * @throws Exception
     */
    public FormSubmissionListResponse listFormSubmissions(String formId, FormSubmissionListRequest query) throws Exception;

    /**
     * Download all submissions of a form as CSV. Requires a scoped API key with the forms scope.
     * @param formId UUID of the form
     * @return byte[] CSV content
     * @throws Exception
     */
    public byte[] downloadSubmissionsCsv(String formId) throws Exception;

    /**
     * Download a single submission as CSV. Requires a scoped API key with the forms scope.
     * @param formId UUID of the form
     * @param submissionId UUID of the submission
     * @return byte[] CSV content
     * @throws Exception
     */
    public byte[] downloadSubmissionCsv(String formId, String submissionId) throws Exception;

    /**
     * Download a single submission as PDF. Requires a scoped API key with the forms scope.
     * @param formId UUID of the form
     * @param submissionId UUID of the submission
     * @return byte[] PDF content
     * @throws Exception
     */
    public byte[] downloadSubmissionPdf(String formId, String submissionId) throws Exception;
}
