package com.apex.picloud.controllers;

import com.apex.picloud.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UploadVideoController {
    private final VideoService videoService;

    @PostMapping("/uploadVideoS3")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, String>> uploadVideoS3(@RequestParam("video") MultipartFile video) {
        Map<String, String> response = new HashMap<>();
        if (video.isEmpty()) {
            response.put("message", "Please select a video to upload.");
            return ResponseEntity.badRequest().body(response);
        }
        String url = videoService.uploadVideoToS3(video);
        if (url == null) {
            response.put("message", "Failed to upload video to S3");
            return ResponseEntity.badRequest().body(response);
        }
        response.put("message", "Video uploaded successfully! Path: " + url);
        response.put("url", url);
        return ResponseEntity.ok(response);
    }
}
