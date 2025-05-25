package com.app.restaurant.controller;

import com.app.restaurant.model.AppUser;
import com.app.restaurant.model.Role;
import com.app.restaurant.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("/users")
public class AppUserController {
    @Autowired
    private AppUserService appUserService;

    @GetMapping("/register")
    public String registerNewUser(Model model) {
        model.addAttribute("appUser", new AppUser());
        model.addAttribute("roles", Role.values());
        model.addAttribute("errors", new HashMap<>());
        return "users/register";
    }

    @PostMapping("/register")
    public String registerNewUserPost(@ModelAttribute AppUser appUser, HttpServletRequest request) {

        appUserService.registerNewUser(appUser, request);
        return "redirect:/login/login";
    }
    @GetMapping("/forgot")
    public String forgotPasswordPage() {
        return "users/forgotPassword"; // the template you shared
    }

    @PostMapping("/resetPassword")
    public String processReset(@RequestParam String email, Model model) {
        boolean sent = appUserService.generateResetToken(email);
        if (!sent) {
            model.addAttribute("errorMessage", "Email not found.");
            return "users/forgot-password";
        }
        model.addAttribute("successMessage", "Reset link sent to your email.");
        return "users/forgot-password";
    }

    @GetMapping("/reset")
    public String showResetForm(@RequestParam String token, Model model) {
        if (!appUserService.validateToken(token)) {
            model.addAttribute("errorMessage", "Token is invalid or expired.");
            return "users/forgot-password";
        }
        model.addAttribute("token", token);
        return "users/reset-password-form";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam String token, @RequestParam String password, Model model) {
        if (appUserService.updatePassword(token, password)) {
            return "redirect:/login?resetSuccess";
        }
        model.addAttribute("errorMessage", "Reset failed. Try again.");
        return "users/reset-password-form";
    }


}
