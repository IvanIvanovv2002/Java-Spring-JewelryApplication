package com.example.jewelryspringapplication.Pointcuts;

import com.example.jewelryspringapplication.Models.Users.User;
import com.example.jewelryspringapplication.Services.AuthenticationService.UserService;
import com.example.jewelryspringapplication.Services.EmailService.EmailServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.Optional;

@Aspect
@Component
public class RegistrationPointcut {

    private final EmailServiceImpl emailService;
    private final UserService userService;

    public RegistrationPointcut(EmailServiceImpl emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @Pointcut(value = "execution(* com.example.jewelryspringapplication.Services.AuthenticationService.UserService.registerUser(..)) && args(email,password)", argNames = "email,password")
    public void sendRegistrationCodePointcut(String email,String password) { }

    @After(value = "sendRegistrationCodePointcut(email, password)", argNames = "email,password")
    public void sendRegistrationCode(String email, String password) {
        this.emailService.emailVerificationPostRegister(email, userRegistrationCode(email));
    }

    @ModelAttribute
    public String userRegistrationCode(String email) {
        Optional<User> user = this.userService.findUserByEmail(email);
        return user.map(User::getRegistrationCode).orElse(null);
    }

}
