package com.example.jewelryspringapplication.Services.UploadServices;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BaseUploadService {

    String uploadFile(MultipartFile multipartFile) throws IOException;
    String uploadVideo(MultipartFile multipartFile) throws IOException;

}
