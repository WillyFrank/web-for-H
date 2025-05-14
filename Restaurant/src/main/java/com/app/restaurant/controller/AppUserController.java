package com.app.restaurant.controller;

import com.app.restaurant.model.AppUser;
import com.app.restaurant.model.Role;
import com.app.restaurant.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
