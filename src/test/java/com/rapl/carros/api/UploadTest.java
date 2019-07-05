package com.rapl.carros.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.rapl.carros.CarrosApiApplication;
import com.rapl.carros.api.upload.UploadInput;
import com.rapl.carros.api.upload.UploadOutput;
import com.rapl.carros.domain.FirebaseStorageService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarrosApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UploadTest {

	@Autowired
    protected TestRestTemplate rest;

    @Autowired
    private FirebaseStorageService service;

    private TestRestTemplate basicAuth() {
        return rest.withBasicAuth("admin","admin");
    }

    private UploadInput getUploadInput() {
        UploadInput upload = new UploadInput();
        upload.setFileName("nome.txt");
        // Base64 de Roberto Atanásio
        upload.setBase64("Um9iZXJ0byBBdGFuw6FzaW8=");
        upload.setMimeType("text/plain");
        return upload;
    }

    @Test
    public void testUploadFirebase() {
        String url = service.upload(getUploadInput());

        // Faz o Get na URL
        ResponseEntity<String> urlResponse = rest.getForEntity(url, String.class);
        System.out.println(">>>>>>>> urlResponse: " + urlResponse);
        assertEquals(HttpStatus.OK, urlResponse.getStatusCode());
    }

    @Test
    public void testUploadAPI() {

        UploadInput upload = getUploadInput();

        // Insert
        ResponseEntity<UploadOutput> response = basicAuth().postForEntity("/api/v1/upload", upload, UploadOutput.class);
        System.out.println("<<<<<<<< response: " + response);

        // Verifica se criou
        assertEquals(HttpStatus.OK, response.getStatusCode());

        UploadOutput out = response.getBody();
        assertNotNull(out);
        System.out.println(">>>>>>> out: " + out);

        String url = out.getUrl();

        // Faz o Get na URL
        ResponseEntity<String> urlResponse = rest.getForEntity(url, String.class);
        System.out.println(">>>>>>>>> urlResponse: " + urlResponse);
        assertEquals(HttpStatus.OK, urlResponse.getStatusCode());
    }
}
