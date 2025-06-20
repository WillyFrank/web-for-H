package com.app.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class AppUser {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String email;
    private String password;
    @Transient
    private String passwordConfirmation;
    private Boolean enabled;
    @Enumerated(EnumType.STRING)
    private Role role;

}
