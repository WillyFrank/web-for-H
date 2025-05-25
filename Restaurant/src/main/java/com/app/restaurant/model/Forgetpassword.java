package com.app.restaurant.model;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

public class Forgetpassword {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String token;

        private LocalDateTime expiryDate;

        @ManyToOne
        private AppUser user;

        public boolean isExpired() {
            return LocalDateTime.now().isAfter(expiryDate);
        }
    }

