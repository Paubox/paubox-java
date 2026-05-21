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

import com.paubox.data.Form;
import com.paubox.data.FormSubmissionAttachment;
import com.paubox.data.FormSubmissionRequest;
import com.paubox.service.FormsInterface;
import com.paubox.service.FormsService;

public class TestFormsService {

    static FormsInterface forms = new FormsService();
    static String validFormId;
    static String invalidFormId = "00000000-0000-0000-0000-000000000000";

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
        } catch (Exception e) {
            validFormId = null;
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
}
