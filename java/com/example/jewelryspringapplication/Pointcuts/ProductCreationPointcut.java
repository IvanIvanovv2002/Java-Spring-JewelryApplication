package com.example.jewelryspringapplication.Pointcuts;

import com.example.jewelryspringapplication.Models.Users.User;
import com.example.jewelryspringapplication.Services.AuthenticationService.UserService;
import com.example.jewelryspringapplication.Services.EmailService.EmailServiceImpl;
import jakarta.mail.MessagingException;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Aspect
@Component
public class ProductCreationPointcut {

    private final EmailServiceImpl emailService;
    private final UserService userService;

    public ProductCreationPointcut(EmailServiceImpl emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @Pointcut(value = "execution(* com.example.jewelryspringapplication.Services.JewelryServices.JewelryFactoryService.createJewelry(..)) && " +
    "args(name,price,gemstone,type,origin,keyWords,notInStock,mainImage,images,video,description)",
    argNames = "name,price,gemstone,type,origin,keyWords,notInStock,mainImage,images,video,description")
    public void productCreationPointcut(String name, Double price, String gemstone, String type, String origin, String keyWords,
    Boolean notInStock, MultipartFile mainImage, List<MultipartFile> images,MultipartFile video,String description) { }


    @After(value = "productCreationPointcut(name,price,gemstone,type,origin,keyWords,notInStock,mainImage,images,video,description)", argNames = "name,price,gemstone,type,origin,keyWords,notInStock,mainImage,images,video,description")
    public void sendMessageToSubscribers(String name, Double price, String gemstone, String type, String origin, String keyWords, Boolean notInStock, MultipartFile mainImage, List<MultipartFile> images,MultipartFile video,String description) throws MessagingException {
        List<User> subscribedUsers = this.userService.findSubscribedUsers();
        this.emailService.emailMessageOnNewPost(name,description,images,subscribedUsers);
    }



}
