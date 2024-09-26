package com.example.jewelryspringapplication.Services.EmailService;

import com.example.jewelryspringapplication.Models.Users.User;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;


    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.mailSender = javaMailSender;
    }

    @Override
    public void emailVerificationPostRegister(String userEmail, String verificationId) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        try {
            messageHelper.setFrom("ivanoviatelierjewelries@gmail.com");
            messageHelper.setTo(userEmail);
            messageHelper.setSubject("Потвърждение на акаунт.");

            String verificationLink = "http://localhost:1010/email/verification/" + verificationId;

            String emailContent = "За да потвърдите акаунта и да можете да то използвате, кликнете на този линк: " + verificationLink;
            messageHelper.setText(emailContent);

            mailSender.send(messageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendContactMessage(String senderEmail, String subject, String messageContent) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        try {
            messageHelper.setFrom(senderEmail);
            messageHelper.setTo("ivanoviatelierjewelries@gmail.com");
            messageHelper.setSubject(subject);

            String emailContent = "Изпратено от: " + senderEmail + "\n\n" + messageContent;
            messageHelper.setText(emailContent);

            mailSender.send(messageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void emailMessageOnNewPost(String name, String description, List<MultipartFile> images, List<User> subscribedUsers) throws MessagingException {
        for (User user : subscribedUsers) {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            try {
                messageHelper.setFrom("ivanoviatelierjewelries@gmail.com");
                messageHelper.setTo(user.getEmail());
                messageHelper.setSubject("Нов продукт");

                // Construct email content
                StringBuilder emailContent = new StringBuilder();
                emailContent.append("Драги ").append(user.getEmail()).append(",\n\n");
                emailContent.append("Ivanovi Atelier разполагат с нов продукт !").append(name).append("\n\n");
                emailContent.append("Description: ").append(description).append("\n\n");

                // Adding images as attachments
                for (MultipartFile image : images) {
                    messageHelper.addAttachment(Objects.requireNonNull(image.getOriginalFilename()), image);
                }

                messageHelper.setText(emailContent.toString());

                mailSender.send(messageHelper.getMimeMessage());
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void sendEmailUpdatedAccount(String email) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        try {
            messageHelper.setFrom("ivanoviatelierjewelries@gmail.com");
            messageHelper.setTo(email);
            messageHelper.setSubject("Паролата ви беше променена.");

            String emailContent = "Здравейте, паролата ви беше променена и ако това не бяхте вие, то тогава може да се свържете с нас на телефон 089 776 3197 | 089 850 3538";

            messageHelper.setText(emailContent);

            mailSender.send(messageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendEmailAfterVerifiedOrder(String email) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        try {
            messageHelper.setFrom("ivanoviatelierjewelries@gmail.com");
            messageHelper.setTo(email);
            messageHelper.setSubject("Вашата поръчка е потвърдена");

            String emailContent = "Вашата поръчка беше потвърдена и изпратена по куриера упоменат от вас. Очаквайте скоро събощение от доставчика !";

            messageHelper.setText(emailContent);

            mailSender.send(messageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
