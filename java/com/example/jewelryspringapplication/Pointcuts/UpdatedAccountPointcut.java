package com.example.jewelryspringapplication.Pointcuts;

import com.example.jewelryspringapplication.Models.Users.User;
import com.example.jewelryspringapplication.Services.EmailService.EmailService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class UpdatedAccountPointcut {

    private final EmailService emailService;

    public UpdatedAccountPointcut(EmailService emailService) {
        this.emailService = emailService;
    }

    @Pointcut(value = "execution(* com.example.jewelryspringapplication.Services.AuthenticationService.UserService.changePassword(..)) && args(newPassword, user)", argNames = "newPassword,user")
    public void updateProfilePointcut(String newPassword, User user) { }


    @After(value = "updateProfilePointcut(newPassword,user)", argNames = "newPassword,user")
    public void afterChangePassword(String newPassword,User user) {
        this.emailService.sendEmailUpdatedAccount(user.getEmail());
    }


}
