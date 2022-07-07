package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.SignupForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getSignUpPage(@ModelAttribute("signupForm") SignupForm signupForm){
        return "signup";
    }

    @PostMapping
    public String createAccount(@ModelAttribute("signupForm") SignupForm signupForm, RedirectAttributes redirectAttributes){
        User user = new User(null, signupForm.getUsername(), null, signupForm.getPassword(), signupForm.getFirstName(),
                signupForm.getLastName());

        String signupError = null;

        if(userService.isAvailable(signupForm.getUsername())){
            signupError = "Username already exists";
        }

        if (signupError == null) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 1) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if (signupError == null) {
            redirectAttributes.addFlashAttribute("signupSuccess", true);
        } else {
            redirectAttributes.addFlashAttribute("signupFailure", true);
            redirectAttributes.addFlashAttribute("signupErrorMessage", signupError);
        }
        return "redirect:/login";
    }
}
