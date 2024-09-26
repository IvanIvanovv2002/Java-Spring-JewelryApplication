package com.example.jewelryspringapplication.Controllers;

import com.example.jewelryspringapplication.Models.Users.User;
import com.example.jewelryspringapplication.Services.AuthenticationService.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class AuthenticationController {
    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView authenticationPage() {
        return new ModelAndView("authenticate");
    }

    @GetMapping("/session/expired")
    public ModelAndView expiredSession() {
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/login/failed")
    public ModelAndView failedLogin(RedirectAttributes redirectAttr) {
        redirectAttr.addFlashAttribute("errorLogin",true);
        return new ModelAndView("redirect:/login");
    }


    @PostMapping("/register")
    public ModelAndView doRegister(String email, String password, String repeatPassword, RedirectAttributes redirectAttr) {

        this.userService.checkForErrors(email,password,repeatPassword);

        this.userService.registerUser(email,password);

        redirectAttr.addFlashAttribute("registered",true);

        return new ModelAndView("redirect:/");
    }

    @GetMapping("/email/verification/{registrationID}")
    public ModelAndView emailVerification(@PathVariable String registrationID,RedirectAttributes redirectAttr) {
        User user = this.userService.findUserByRegistrationCode(registrationID);
        user.setRegistrationCode(null);
        this.userService.save(user);
        redirectAttr.addFlashAttribute("verifiedAccount",true);
        return new ModelAndView("redirect:/");
    }

}
