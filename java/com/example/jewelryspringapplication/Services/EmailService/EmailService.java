package com.example.jewelryspringapplication.Services.EmailService;

import com.example.jewelryspringapplication.Models.Users.User;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmailService {
    void emailVerificationPostRegister(String userEmail,String registrationCode);
    void sendContactMessage(String email,String topic,String message);
    void emailMessageOnNewPost(String name, String description, List<MultipartFile> images,List<User> subscribedUsers) throws MessagingException;
    void sendEmailUpdatedAccount(String email);
    void sendEmailAfterVerifiedOrder(String email);

}
