package com.example.jewelryspringapplication.Services.UploadServices;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@Service
public class DynamicUploadServiceImpl implements BaseUploadService {

    private final Cloudinary cloudinary;
    private final String path;


    @Autowired
    public DynamicUploadServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
        this.path = "dynamicImages/";
    }

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {

        String publicId = path + UUID.randomUUID();

        Transformation<?> transformation = new Transformation<>().width(1200).quality("auto:best").fetchFormat("auto");

       return cloudinary.uploader().upload(multipartFile.getBytes(), Map.of("public_id", publicId,"transformation",transformation)).get("url").toString();
    }

    @Override
    public String uploadVideo(MultipartFile multipartFile) throws IOException {
        String publicId = path + UUID.randomUUID();
        return cloudinary.uploader().uploadLarge(multipartFile.getBytes(), Map.of("public_id", publicId, "resource_type", "video")).get("url").toString();
    }
}
