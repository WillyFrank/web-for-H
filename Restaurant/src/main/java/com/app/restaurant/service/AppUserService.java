package com.app.restaurant.service;

import com.app.restaurant.model.AppUser;
import com.app.restaurant.model.Forgetpassword;
import com.app.restaurant.repository.AppUserRepository;
import com.app.restaurant.repository.ForgetpasswordRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final ForgetpasswordRepository tokenRepository;
    private final EmailService emailService; // Assume you have this

    public AppUserService(AppUserRepository appUserRepository,
                          PasswordEncoder passwordEncoder,
                          ForgetpasswordRepository tokenRepository,
                          EmailService emailService) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    public void registerNewUser(AppUser appUser , HttpServletRequest reques) {
        if (appUser == null) throw new NullPointerException("APP USER IS NULL");

        appUser.setEnabled(true);
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUserRepository.save(appUser);
    }

    public boolean generateResetToken(String email) {
        AppUser user = appUserRepository.findByUsername(email);
        if (user == null) return false;

        String token = UUID.randomUUID().toString();
        Forgetpassword resetToken = Forgetpassword.builder()
                .token(token)
                .expiryDate(LocalDateTime.now().plusMinutes(30))
                .user(user)
                .build();

        tokenRepository.save(resetToken);

        String link = "http://localhost:8080/users/reset?token=" + token;
        emailService.send(email, "Reset your password", "Click here to reset: " + link);

        return true;
    }

    public boolean validateToken(String token) {
        Forgetpassword resetToken = tokenRepository.findByToken(token);
         resetToken = tokenRepository.findByToken(token);
        return resetToken != null && !resetToken.isExpired();
    }

    public boolean updatePassword(String token, String newPassword) {
       Forgetpassword resetToken = tokenRepository.findByToken(token);
        if (resetToken == null || resetToken.isExpired()) return false;

        AppUser user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        appUserRepository.save(user);
        tokenRepository.delete(resetToken);
        return true;
    }
}
