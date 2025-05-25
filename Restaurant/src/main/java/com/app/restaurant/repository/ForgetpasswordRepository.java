package com.app.restaurant.repository;
import com.app.restaurant.model.Forgetpassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgetpasswordRepository extends JpaRepository<Forgetpassword, Long> {
    Forgetpassword findByToken(String token);
}
