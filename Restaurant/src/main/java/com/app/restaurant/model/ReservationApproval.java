package com.app.restaurant.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
public class ReservationApproval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    private LocalDateTime reservationDateTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    private int guestCount;

    @Column(length = 1000)
    private String comments;

    }
