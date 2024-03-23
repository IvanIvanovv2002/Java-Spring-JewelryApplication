package com.example.jewelryspringapplication.Controllers;

import com.example.jewelryspringapplication.Services.AuthenticationService.UserService;
import com.example.jewelryspringapplication.Services.EmailService.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MessagingController {
    private final UserService userService;
    private final EmailService emailService;

    public MessagingController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/contacts")
    public ModelAndView contactPage() {
        return new ModelAndView("contact");
    }

    @PostMapping("/contacts/sendMessage")
    public ModelAndView sendMessage(String email, String topic, String message, RedirectAttributes redirectAttr) {
        this.userService.checkForExistingEmail(email);
        this.emailService.sendContactMessage(email,topic,message);
        redirectAttr.addFlashAttribute("successfullySentMessage",true);
        return new ModelAndView("redirect:/contacts");
    }

}
