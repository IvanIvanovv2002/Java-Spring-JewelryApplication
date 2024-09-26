package com.example.jewelryspringapplication.Controllers.ErrorHandler;

import com.example.jewelryspringapplication.Exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ErrorHandlerController {

    @ExceptionHandler(PresentEmailException.class)
    public ModelAndView presentEmailException(RedirectAttributes redirectAttr) {
         redirectAttr.addFlashAttribute("emailPresent",true);
         return new ModelAndView("redirect:/login");
    }

    @ExceptionHandler(AccountVerificationException.class)
    public ModelAndView accountNotVerifiedException(RedirectAttributes redirectAttr) {
        redirectAttr.addFlashAttribute("accountNotVerified",true);
        return new ModelAndView("redirect:/login");
    }

    @ExceptionHandler(PasswordsNotMatchingException.class)
    public ModelAndView passwordsNotMatchingException(RedirectAttributes redirectAttr) {
        redirectAttr.addFlashAttribute("errorRegister",true);
        return new ModelAndView("redirect:/login");
    }

    @ExceptionHandler(PasswordChangingException.class)
    public ModelAndView passwordChangingException(RedirectAttributes redirectAttr) {
        redirectAttr.addFlashAttribute("passwordChangeIssue",true);
        return new ModelAndView("redirect:/profile");
    }

    @ExceptionHandler(SamePasswordsException.class)
    public ModelAndView samePasswordsException(RedirectAttributes redirectAttr) {
        redirectAttr.addFlashAttribute("samePasswordIssue",true);
        return new ModelAndView("redirect:/profile");
    }

    @ExceptionHandler(EmailNotExistingException.class)
    public ModelAndView emailNotExistingException(RedirectAttributes redirectAttr) {
        redirectAttr.addFlashAttribute("EmailNotExisting",true);
        return new ModelAndView("redirect:/contacts");
    }

    @ExceptionHandler(WrongLinkedEmailException.class)
    public ModelAndView wrongLinkedEmail(RedirectAttributes redirectAttr) {
        redirectAttr.addFlashAttribute("wrongEmailForSubscription",true);
        return new ModelAndView("redirect:/");
    }
}
