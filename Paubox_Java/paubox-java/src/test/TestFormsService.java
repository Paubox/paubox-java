package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.paubox.data.CreateFormRequest;
import com.paubox.data.Form;
import com.paubox.data.FormListRequest;
import com.paubox.data.FormListResponse;
import com.paubox.data.FormStats;
import com.paubox.data.FormSubmission;
import com.paubox.data.FormSubmissionAttachment;
import com.paubox.data.FormSubmissionListResponse;
import com.paubox.data.FormSubmissionRequest;
import com.paubox.data.UpdateFormRequest;
import com.paubox.data.UpdateFormResponse;
import com.paubox.service.FormsInterface;
import com.paubox.service.FormsService;

public class TestFormsService {

    static FormsInterface forms = new FormsService();
    static FormsInterface authForms;
    static String validFormId;
    static String invalidFormId = "00000000-0000-0000-0000-000000000000";
    static String formsApiKey;
    static String customerId;

    @BeforeClass
    public static void init() {
        String propertiesFile = System.getProperty("properties");
        if (propertiesFile == null || propertiesFile.equals("")) {
            propertiesFile = "src/test/config.properties";
        }
        try {
            java.util.Properties props = new java.util.Properties();
            props.load(new java.io.FileInputStream(propertiesFile));
            validFormId = props.getProperty("FORM_ID");
            formsApiKey = props.getProperty("FORMSAPIKEY");
            customerId = props.getProperty("CUSTOMER_ID");
        } catch (Exception e) {
            validFormId = null;
            formsApiKey = null;
            customerId = null;
        }
        if (formsApiKey != null && !formsApiKey.isEmpty()) {
            authForms = new FormsService(formsApiKey);
        }
    }

    @Test
    public void testGetFormForSuccess() throws Exception {
        if (validFormId == null || validFormId.isEmpty()) return;
        Form form = forms.getForm(validFormId);
        assertNotNull(form);
        assertNotNull(form.getId());
        assertEquals(validFormId, form.getId());
        assertNotNull(form.getTitle());
    }

    @Test(expected = Exception.class)
    public void testGetFormForInvalidId() throws Exception {
        forms.getForm(invalidFormId);
    }

    @Test
    public void testSubmitFormForSuccess() throws Exception {
        if (validFormId == null || validFormId.isEmpty()) return;
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("test_field", "test_value");
        FormSubmissionRequest submission = new FormSubmissionRequest(data);
        forms.submitForm(validFormId, submission);
    }

    @Test(expected = Exception.class)
    public void testSubmitFormForMissingFormData() throws Exception {
        if (validFormId == null || validFormId.isEmpty()) {
            throw new Exception("form_data cannot be null.");
        }
        FormSubmissionRequest submission = new FormSubmissionRequest();
        forms.submitForm(validFormId, submission);
    }

    @Test(expected = Exception.class)
    public void testSubmitFormForInvalidFormId() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("test_field", "test_value");
        FormSubmissionRequest submission = new FormSubmissionRequest(data);
        forms.submitForm(invalidFormId, submission);
    }

    @Test
    public void testSubmitFormWithAttachment() throws Exception {
        if (validFormId == null || validFormId.isEmpty()) return;
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("test_field", "test_value");
        FormSubmissionRequest submission = new FormSubmissionRequest(data);

        List<FormSubmissionAttachment> attachments = new ArrayList<FormSubmissionAttachment>();
        attachments.add(new FormSubmissionAttachment("hello.txt", "SGVsbG8gV29ybGQh"));
        submission.setAttachments(attachments);

        forms.submitForm(validFormId, submission);
    }

    @Test
    public void testListFormsForSuccess() throws Exception {
        if (formsApiKey == null || formsApiKey.isEmpty()) return;
        if (customerId == null || customerId.isEmpty()) return;
        FormListRequest query = new FormListRequest();
        query.setCustomerId(Integer.valueOf(customerId));
        query.setItems(10);
        FormListResponse response = authForms.listForms(query);
        assertNotNull(response);
        assertNotNull(response.getResults());
        assertNotNull(response.getPageInfo());
    }

    @Test(expected = Exception.class)
    public void testListFormsWithoutApiKeyThrows() throws Exception {
        new FormsService("").listForms(null);
    }

    @Test
    public void testFormLifecycle() throws Exception {
        if (formsApiKey == null || formsApiKey.isEmpty()) return;
        if (customerId == null || customerId.isEmpty()) return;

        Map<String, Object> formJson = new HashMap<String, Object>();
        formJson.put("body", new ArrayList<Object>());
        CreateFormRequest createRequest = new CreateFormRequest("paubox-java SDK test form",
                formJson, Integer.valueOf(customerId), 1);
        createRequest.setActive(false);

        String formId = authForms.createForm(createRequest);
        assertNotNull(formId);

        String copiedFormId = null;
        try {
            Form form = authForms.getFormById(formId);
            assertNotNull(form);
            assertEquals(formId, form.getId());
            assertEquals("paubox-java SDK test form", form.getTitle());

            UpdateFormRequest updateRequest = new UpdateFormRequest();
            updateRequest.setTitle("paubox-java SDK test form (updated)");
            UpdateFormResponse updateResponse = authForms.updateForm(formId, updateRequest);
            assertNotNull(updateResponse);
            assertNotNull(updateResponse.getDetail());

            Form copiedForm = authForms.copyForm(formId, "paubox-java SDK test form (copy)");
            assertNotNull(copiedForm);
            assertNotNull(copiedForm.getId());
            copiedFormId = copiedForm.getId();
            assertTrue(!formId.equals(copiedFormId));
        } finally {
            try {
                authForms.archiveForm(formId);
            } finally {
                if (copiedFormId != null) {
                    authForms.archiveForm(copiedFormId);
                }
            }
        }
    }

    @Test
    public void testGetFormStats() throws Exception {
        if (formsApiKey == null || formsApiKey.isEmpty()) return;
        FormStats stats = authForms.getFormStats(null);
        assertNotNull(stats);
        assertTrue(stats.getActiveFormCount() >= 0);
        assertTrue(stats.getTotalSubmissionCount() >= 0);
        assertTrue(stats.getSubmissionsLast7Days() >= 0);
    }

    @Test
    public void testListFormSubmissions() throws Exception {
        if (formsApiKey == null || formsApiKey.isEmpty()) return;
        if (validFormId == null || validFormId.isEmpty()) return;
        FormSubmissionListResponse response = authForms.listFormSubmissions(validFormId, null);
        assertNotNull(response);
        assertNotNull(response.getData());
    }

    @Test
    public void testDownloadSubmissionsCsv() throws Exception {
        if (formsApiKey == null || formsApiKey.isEmpty()) return;
        if (validFormId == null || validFormId.isEmpty()) return;
        byte[] csv = authForms.downloadSubmissionsCsv(validFormId);
        assertNotNull(csv);
        assertTrue(csv.length > 0);
    }

    @Test
    public void testDownloadSubmissionPdf() throws Exception {
        if (formsApiKey == null || formsApiKey.isEmpty()) return;
        if (validFormId == null || validFormId.isEmpty()) return;
        FormSubmissionListResponse response = authForms.listFormSubmissions(validFormId, null);
        assertNotNull(response);
        assertNotNull(response.getData());
        if (response.getData().isEmpty()) return;
        FormSubmission submission = response.getData().get(0);
        byte[] pdf = authForms.downloadSubmissionPdf(validFormId, submission.getId());
        assertNotNull(pdf);
        assertTrue(pdf.length > 0);
        assertTrue(pdf.length >= 4);
        assertTrue(pdf[0] == '%' && pdf[1] == 'P' && pdf[2] == 'D' && pdf[3] == 'F');
    }
}
