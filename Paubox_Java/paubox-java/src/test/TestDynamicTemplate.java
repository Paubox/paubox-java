package test;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.*;

import com.paubox.service.DynamicTemplateService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
public class TestDynamicTemplate {
    private static final String TEMPLATE_NAME = "test_template";
    private static final String TEMPLATE_FILE_PATH = "template.html";

    private static DynamicTemplateService service;

    @Before
    public void setup() {

        String propertiesFile = System.getProperty("properties");
        if (propertiesFile == null || propertiesFile.equals("")) {
            propertiesFile = "src/test/config.properties";
        }
        ConfigurationManager.getProperties(propertiesFile);

        service = new DynamicTemplateService();

        String text = "<p>Hello, world!</p>";
        File file = new File(TEMPLATE_FILE_PATH);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @After
    public void cleanup() throws IOException {
        // Delete the test template after running the tests
        List<DynamicTemplate> templates = service.getAllDynamicTemplates();
        for (DynamicTemplate template : templates) {
            if (template.getName().equals(TEMPLATE_NAME)) {
                service.deleteDynamicTemplate(String.valueOf(template.getId()));
            }
        }
    }

    @Test
    public void testGetAllDynamicTemplates() throws IOException {
        List<DynamicTemplate> templates = service.getAllDynamicTemplates();
        assertNotNull(templates);
        assertTrue(templates.size() > 0);
    }

    @Test
    public void testCreateDynamicTemplate() throws IOException {
        // Create a test template
        File templateFile = new File(TEMPLATE_FILE_PATH);
        String templateContent = FileUtils.readFileToString(templateFile, StandardCharsets.UTF_8);
        DynamicTemplate template = new DynamicTemplate(TEMPLATE_NAME, templateContent);
        String response = service.createDynamicTemplate(template.getName(), templateFile);
        assertEquals("Message: Template test_template created!", response);
    }

    @Test
    public void testUpdateDynamicTemplate() throws IOException {
        // Create a test template
        File templateFile = new File(TEMPLATE_FILE_PATH);
        String templateContent = FileUtils.readFileToString(templateFile, StandardCharsets.UTF_8);
        DynamicTemplate template = new DynamicTemplate(TEMPLATE_NAME, templateContent);
        String createResponse = service.createDynamicTemplate(template.getName(), templateFile);
        assertEquals("Message: Template test_template created!", createResponse);
        List<DynamicTemplate> all = service.getAllDynamicTemplates();
        template.setId(all.get(all.size() - 1).getId());

        // Update the test template
        String updatedTemplateName = "updated_test_template";
        String updatedTemplateContent = templateContent.replace("{{subject}}", "Updated Subject");
        File updatedTemplateFile = new File(TEMPLATE_FILE_PATH);
        FileUtils.writeStringToFile(updatedTemplateFile, updatedTemplateContent, StandardCharsets.UTF_8);
        String updateResponse = service.updateDynamicTemplate(String.valueOf(template.getId()), updatedTemplateName, updatedTemplateFile);
        assertEquals("Message: Template updated_test_template updated!", updateResponse);

        // Verify that the updated template has the new name and content
        DynamicTemplate updatedTemplate = service.getDynamicTemplate(String.valueOf(template.getId()));
        assertEquals(updatedTemplateName, updatedTemplate.getName());
        assertEquals(updatedTemplateContent, updatedTemplate.getContent());
    }

    @Test
    public void testDeleteDynamicTemplate() throws IOException {
        // Create a test template
        List<DynamicTemplate> all = service.getAllDynamicTemplates();
        DynamicTemplate last = all.get(all.size() - 1);
        DynamicTemplate template = new DynamicTemplate(last.getId(), last.getName(), last.getContent());

        // Delete the test template
        String deleteResponse = service.deleteDynamicTemplate(String.valueOf(template.getId()));
        assertEquals("Message: Template Tempate Name deleted!", deleteResponse);
    
        // Verify that the template has been deleted
        List<DynamicTemplate> templates = service.getAllDynamicTemplates();
        assertFalse(templates.stream().anyMatch(new Predicate<DynamicTemplate>() {
            @Override
            public boolean test(DynamicTemplate t) {
                return t.getName().equals(TEMPLATE_NAME);
            }
        }));
    }
    
    @Test
    public void testGetDynamicTemplate() throws IOException {
        // Create a test template
        File templateFile = new File(TEMPLATE_FILE_PATH);
        String templateContent = FileUtils.readFileToString(templateFile, StandardCharsets.UTF_8);
        DynamicTemplate template = new DynamicTemplate(TEMPLATE_NAME, templateContent);
        String createResponse = service.createDynamicTemplate(template.getName(), templateFile);
        assertEquals("Message: Template test_template created!", createResponse);
        List<DynamicTemplate> all = service.getAllDynamicTemplates();
        template.setId(all.get(all.size() - 1).getId());
        template.setContent(all.get(all.size() - 1).getContent());
        template.setName(all.get(all.size() - 1).getName());
        // Get the test template by ID
        DynamicTemplate retrievedTemplate = service.getDynamicTemplate(String.valueOf(template.getId()));
        assertNotNull(retrievedTemplate);
        assertEquals(template.getId(), retrievedTemplate.getId());
        assertEquals(template.getName(), retrievedTemplate.getName());
        assertEquals(template.getContent(), retrievedTemplate.getContent());
    }
}
