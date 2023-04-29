package test;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.*;

import com.paubox.data.WebhookEndpoint;
import com.paubox.data.WebhookEndpointRequest;
import com.paubox.data.WebhookEndpointResponse;
import com.paubox.service.WebhookEndpointService;

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
public class TestWebhookEndpoint { 
    private static WebhookEndpointRequest reqeustModel = new WebhookEndpointRequest("https://example.com", Arrays.asList("api_mail_log_delivered"), "testSignKey", "testAPIKey");

    private static WebhookEndpointService service;

    @Before
    public void setup() {

        String propertiesFile = System.getProperty("properties");
        if (propertiesFile == null || propertiesFile.equals("")) {
            propertiesFile = "src/test/config.properties";
        }
        ConfigurationManager.getProperties(propertiesFile);

        service = new WebhookEndpointService();
    }

    @After
    public void cleanup() throws IOException {
         
    }

    @Test
    public void testGetAllDynamicTemplates() throws IOException {
        List<WebhookEndpoint> templates = service.getAllWebhookEndpoints();
        assertNotNull(templates);
        assertTrue(templates.size() > 0);
    }

    @Test
    public void testCreateDynamicTemplate() throws IOException {
        WebhookEndpointResponse response = service.createWebhookEndpoint(reqeustModel);
        assertEquals("Webhook created!", response.getMessage());

        WebhookEndpointResponse responseDeleted = service.deleteWebhookEndpoint(response.getData().getId());
        assertEquals("Webhook deleted!", responseDeleted.getMessage());

    }

    @Test
    public void testUpdateDynamicTemplate() throws IOException {
        WebhookEndpointResponse response = service.createWebhookEndpoint(reqeustModel);
        assertEquals("Webhook created!", response.getMessage());
        string targetURL = "https://newExample.com";
        reqeustModel.setTargetUrl(targetURL);
        WebhookEndpointResponse responseUpdated = service.updateWebhookEndpoint(response.getData().getId(), reqeustModel);

        assertEquals("Webhook updated!", responseUpdated.getMessage());
        assertEquals(targetURL, responseUpdated.getData().getTargetUrl());

        WebhookEndpointResponse responseDeleted = service.deleteWebhookEndpoint(response.getData().getId());
        assertEquals("Webhook deleted!", responseDeleted.getMessage());

    }
    
    @Test
    public void testGetDynamicTemplate() throws IOException {
        
        List<WebhookEndpoint> templates = service.getAllWebhookEndpoints();
        assertNotNull(templates);
        assertTrue(templates.size() > 0); 

        WebhookEndpoint templatesSingle = service.getWebhookEndpoint(templates.get(templates.size() - 1).getId());
        assertNotNull(templatesSingle);
        assertNotNull(templatesSingle.getId());
        assertNotNull(templatesSingle.getTargetUrl());
        assertNotNull(templatesSingle.getSigningKey());
    }
}
