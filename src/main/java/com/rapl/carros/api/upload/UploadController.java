package com.rapl.carros.api.upload;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rapl.carros.domain.FirebaseStorageService;

@RestController
@RequestMapping("/api/v1/upload")
public class UploadController {

	@Autowired
    private FirebaseStorageService uploadService;

//    @PostMapping("/upload")
//    public ResponseEntity upload(@RequestParam String fileName, @RequestParam String base64) {
//
//        String s = "Filename: " + fileName + " >> base64 > " + base64;
//
//        return ResponseEntity.ok(s);
//    }

    @PostMapping
    public ResponseEntity<UploadOutput> upload(@RequestBody UploadInput uploadInput) throws IOException {

//        String url = "Filename: " + uploadInput.getFileName() + " >> base64 > " + uploadInput.getBase64();

        String url = uploadService.upload(uploadInput);

        return ResponseEntity.ok(new UploadOutput(url));
    }
}
