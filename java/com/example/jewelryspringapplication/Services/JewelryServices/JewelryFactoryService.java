package com.example.jewelryspringapplication.Services.JewelryServices;

import com.example.jewelryspringapplication.Models.Jewelries.*;
import com.example.jewelryspringapplication.Repositories.*;
import com.example.jewelryspringapplication.Services.UploadServices.DynamicUploadServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.jewelryspringapplication.Constants.BasicConstants.*;

@Service
public class JewelryFactoryService {

    private final DynamicUploadServiceImpl dynamicUploadService;
    private final ImageRepository imageRepository;
    private final BaseJewelryRepository baseJewelryRepository;

    @Autowired
    public JewelryFactoryService(DynamicUploadServiceImpl dynamicUploadService, ImageRepository imageRepository, BaseJewelryRepository baseJewelryRepository) {
        this.dynamicUploadService = dynamicUploadService;
        this.imageRepository = imageRepository;
        this.baseJewelryRepository = baseJewelryRepository;
    }

    public void createJewelry(String name, Double price, String gemstone, String type, String origin, String keyWords, Boolean notInStock,
    MultipartFile mainImage, List<MultipartFile> images, MultipartFile video, String description) throws IOException {

       BaseEntity jewelry;

       List<Image> additionalImages = new ArrayList<>();

       String mainImagePath = dynamicUploadService.uploadFile(mainImage);

       String videoPath;

        for ( MultipartFile file : images ) {
            Image image = new Image(dynamicUploadService.uploadFile(file));
            this.imageRepository.save(image);
            additionalImages.add(image);
        }

        Image mainImageCopy = new Image(mainImagePath);
        this.imageRepository.save(mainImageCopy);
        additionalImages.add(mainImageCopy);

        switch (type) {
            case BRACELET -> jewelry = createBracelet(name,price,gemstone,keyWords,notInStock,mainImagePath,additionalImages,description,origin);
            case RING -> jewelry = createRing(name,price,gemstone,keyWords,notInStock,mainImagePath,additionalImages,description,origin);
            case PENDANT ->  jewelry = createPendant(name,price,gemstone,keyWords,notInStock,mainImagePath,additionalImages,description,origin);
            case EARRING -> jewelry = createEarring(name,price,gemstone,keyWords,notInStock,mainImagePath,additionalImages,description,origin);
            case GEMSTONE -> jewelry = createGemstone(name,price,gemstone,keyWords,origin,notInStock,mainImagePath,additionalImages,description);
            default -> { return; }
        }

        if (video != null && !video.isEmpty()) { videoPath = dynamicUploadService.uploadVideo(video) ;jewelry.setVideo(videoPath);}

        this.baseJewelryRepository.save(jewelry);
    }

    private Gemstone createGemstone(String name, Double price, String gemstone, String keywords, String origin, Boolean notInStock,
                                    String mainImagePath, List<Image> additionalImages, String description) {
        return new Gemstone(name,price,gemstone,keywords,notInStock,description,mainImagePath,additionalImages,origin);
    }

    private Earring createEarring(String name, Double price, String gemstone, String keywords, Boolean notInStock, String path300x260, List<Image> currentImages, String description, String origin) {
        return new Earring(name,price,gemstone,keywords,notInStock,description,path300x260,currentImages,origin);
    }

    private Pendant createPendant(String name, Double price, String gemstone,String keywords, Boolean notInStock, String path300x260, List<Image> currentImages, String description,String origin) {
        return new Pendant(name,price,gemstone,keywords,notInStock,description,path300x260,currentImages,origin);
    }

    private Ring createRing(String name, Double price, String gemstone,String keywords, Boolean notInStock, String path300x260, List<Image> currentImages, String description,String origin) {
        return new Ring(name,price,gemstone,keywords,notInStock,description,path300x260,currentImages,origin);
    }

    private Bracelet createBracelet(String name, Double price, String gemstone, String keywords, Boolean notInStock, String path300x260, List<Image> images, String description, String origin) {
        return new Bracelet(name,price,gemstone,keywords,notInStock,description,path300x260,images,origin);
    }

}
