package com.apex.picloud.services;

import org.springframework.web.multipart.MultipartFile;

public interface S3AWSService {
    String uploadFile (MultipartFile file);
}
