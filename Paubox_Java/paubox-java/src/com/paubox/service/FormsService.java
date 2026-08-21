package com.paubox.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.regex.Pattern;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paubox.common.Constants;
import com.paubox.data.CopyFormRequest;
import com.paubox.data.CreateFormRequest;
import com.paubox.data.CreateFormResponse;
import com.paubox.data.Form;
import com.paubox.data.FormListRequest;
import com.paubox.data.FormListResponse;
import com.paubox.data.FormResponse;
import com.paubox.data.FormStats;
import com.paubox.data.FormSubmissionListRequest;
import com.paubox.data.FormSubmissionListResponse;
import com.paubox.data.FormSubmissionRequest;
import com.paubox.data.UpdateFormRequest;
import com.paubox.data.UpdateFormResponse;

public class FormsService implements FormsInterface {

    private static final String FORMS_BASE_URL = "https://api.paubox.com/forms";
    private static final Pattern UUID_PATTERN = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    private final String apiKey;

    public FormsService() {
        this.apiKey = Constants.FORMS_API_KEY;
    }

    public FormsService(String apiKey) {
        this.apiKey = apiKey;
    }

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

    public FormListResponse listForms(FormListRequest query) throws Exception {
        StringBuilder queryString = new StringBuilder();
        if (query != null) {
            appendParam(queryString, "customer_id", query.getCustomerId());
            appendParam(queryString, "form_id", query.getFormId());
            appendParam(queryString, "search", query.getSearch());
            appendParam(queryString, "order", query.getOrder());
            appendParam(queryString, "order_by", query.getOrderBy());
            appendParam(queryString, "archived", query.getArchived());
            appendParam(queryString, "active", query.getActive());
            appendParam(queryString, "page", query.getPage());
            appendParam(queryString, "items", query.getItems());
        }
        String url = FORMS_BASE_URL + "/api/forms" + queryString.toString();
        String responseStr = APIHelper.callToAPIByGet(url, authHeader());
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        FormListResponse response = mapper.readValue(responseStr, FormListResponse.class);
        if (response.getResults() == null || response.getPageInfo() == null) {
            throw new IOException(responseStr);
        }
        return response;
    }

    public String createForm(CreateFormRequest request) throws Exception {
        if (request == null) {
            throw new Exception("CreateFormRequest cannot be null.");
        }
        if (request.getTitle() == null || request.getFormJson() == null
                || request.getCustomerId() == null || request.getVersion() == null) {
            throw new Exception("title, form_json, customer_id and version cannot be null.");
        }
        String url = FORMS_BASE_URL + "/api/forms";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String requestBody = mapper.writeValueAsString(request);
        String responseStr = APIHelper.callToAPIByPost(url, authHeader(), requestBody);
        CreateFormResponse response = mapper.readValue(responseStr, CreateFormResponse.class);
        if (response.getId() == null) {
            throw new IOException(responseStr);
        }
        return response.getId();
    }

    public Form getFormById(String formId) throws Exception {
        validateUuid(formId, "formId");
        String url = FORMS_BASE_URL + "/api/forms/" + formId;
        String responseStr = APIHelper.callToAPIByGet(url, authHeader());
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        FormResponse response = mapper.readValue(responseStr, FormResponse.class);
        if (response.getData() == null || response.getData().getId() == null) {
            throw new IOException(responseStr);
        }
        return response.getData();
    }

    public UpdateFormResponse updateForm(String formId, UpdateFormRequest request) throws Exception {
        validateUuid(formId, "formId");
        if (request == null) {
            throw new Exception("UpdateFormRequest cannot be null.");
        }
        String url = FORMS_BASE_URL + "/api/forms/" + formId;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String requestBody = mapper.writeValueAsString(request);
        String responseStr = APIHelper.callToAPIByPut(url, authHeader(), requestBody);
        UpdateFormResponse response = mapper.readValue(responseStr, UpdateFormResponse.class);
        if (response.getDetail() == null) {
            throw new IOException(responseStr);
        }
        return response;
    }

    public void archiveForm(String formId) throws Exception {
        validateUuid(formId, "formId");
        String url = FORMS_BASE_URL + "/api/forms/" + formId + "/archive";
        postWithoutBody(url);
    }

    public void unarchiveForm(String formId) throws Exception {
        validateUuid(formId, "formId");
        String url = FORMS_BASE_URL + "/api/forms/" + formId + "/unarchive";
        postWithoutBody(url);
    }

    public Form copyForm(String formId, String newTitle) throws Exception {
        validateId(formId, "formId");
        if (newTitle == null || newTitle.isEmpty()) {
            throw new Exception("newTitle cannot be null or empty.");
        }
        String url = FORMS_BASE_URL + "/api/forms/copy";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String requestBody = mapper.writeValueAsString(new CopyFormRequest(formId, newTitle));
        String responseStr = APIHelper.callToAPIByPost(url, authHeader(), requestBody);
        Form form = mapper.readValue(responseStr, Form.class);
        if (form.getId() == null) {
            throw new IOException(responseStr);
        }
        return form;
    }

    public FormStats getFormStats(Integer customerId) throws Exception {
        String url = FORMS_BASE_URL + "/api/forms/stats";
        if (customerId != null) {
            url = url + "?customer_id=" + customerId;
        }
        String responseStr = APIHelper.callToAPIByGet(url, authHeader());
        if (responseStr == null || !responseStr.contains("\"active_form_count\"")) {
            throw new IOException(responseStr);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(responseStr, FormStats.class);
    }

    public FormSubmissionListResponse listFormSubmissions(String formId, FormSubmissionListRequest query) throws Exception {
        validateUuid(formId, "formId");
        StringBuilder queryString = new StringBuilder();
        if (query != null) {
            appendParam(queryString, "submission_id", query.getSubmissionId());
            appendParam(queryString, "order", query.getOrder());
            appendParam(queryString, "order_by", query.getOrderBy());
            appendParam(queryString, "page", query.getPage());
            appendParam(queryString, "items", query.getItems());
        }
        String url = FORMS_BASE_URL + "/api/forms/" + formId + "/submissions" + queryString.toString();
        String responseStr = APIHelper.callToAPIByGet(url, authHeader());
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        FormSubmissionListResponse response = mapper.readValue(responseStr, FormSubmissionListResponse.class);
        if (response.getData() == null) {
            throw new IOException(responseStr);
        }
        return response;
    }

    public byte[] downloadSubmissionsCsv(String formId) throws Exception {
        validateUuid(formId, "formId");
        String url = FORMS_BASE_URL + "/api/forms/" + formId + "/submissions/submission-csv";
        return APIHelper.callToAPIByGetBytes(url, authHeader());
    }

    public byte[] downloadSubmissionCsv(String formId, String submissionId) throws Exception {
        validateUuid(formId, "formId");
        validateUuid(submissionId, "submissionId");
        String url = FORMS_BASE_URL + "/api/forms/" + formId + "/submissions/submission-csv/" + submissionId;
        return APIHelper.callToAPIByGetBytes(url, authHeader());
    }

    public byte[] downloadSubmissionPdf(String formId, String submissionId) throws Exception {
        validateUuid(formId, "formId");
        validateUuid(submissionId, "submissionId");
        String url = FORMS_BASE_URL + "/api/forms/" + formId + "/submissions/" + submissionId + "/submission-pdf";
        return APIHelper.callToAPIByGetBytes(url, authHeader());
    }

    private String authHeader() throws Exception {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new Exception("A scoped API key with the 'forms' scope is required for this endpoint. "
                    + "Set FORMSAPIKEY in your config.properties or use new FormsService(apiKey).");
        }
        return "Bearer " + apiKey;
    }

    private void postWithoutBody(String url) throws Exception {
        String responseStr = APIHelper.callToAPIByPost(url, authHeader(), "");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        UpdateFormResponse response = mapper.readValue(responseStr, UpdateFormResponse.class);
        if (response.getDetail() == null) {
            throw new IOException(responseStr);
        }
    }

    private static void validateId(String value, String name) throws Exception {
        if (value == null || value.isEmpty()) {
            throw new Exception(name + " cannot be null or empty.");
        }
    }

    private static void validateUuid(String value, String name) throws Exception {
        if (value == null || value.isEmpty()) {
            throw new Exception(name + " cannot be null or empty.");
        }
        if (!UUID_PATTERN.matcher(value).matches()) {
            throw new Exception(name + " must be a valid UUID (8-4-4-4-12 hex).");
        }
    }

    private static void appendParam(StringBuilder queryString, String name, Object value) throws Exception {
        if (value == null) {
            return;
        }
        queryString.append(queryString.length() == 0 ? "?" : "&");
        queryString.append(name).append("=").append(URLEncoder.encode(String.valueOf(value), "UTF-8"));
    }
}
