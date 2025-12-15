package com.brahma.yogapeeth.backend.service;

import org.springframework.web.multipart.MultipartFile;

public interface AwsS3Service {


    String uploadFile(MultipartFile file);

    boolean deleteFile(String filename);

}
