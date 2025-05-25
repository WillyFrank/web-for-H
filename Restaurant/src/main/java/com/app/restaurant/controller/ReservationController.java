package com.app.restaurant.controller;
import com.app.restaurant.model.Reservation;
import com.app.restaurant.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    // Main reservation page
    @GetMapping("/reservations")
    public String showReservationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        try {
            model.addAttribute("reservations", reservationService.getAllReservations());
        } catch (Exception e) {
            model.addAttribute("reservations", new ArrayList<>());
            model.addAttribute("error", "Could not load existing reservations: " + e.getMessage());
        }
        return "users/reservations";
    }

    // Handle form submission
    @PostMapping("/reservations/add")
    public String makeReservation(@ModelAttribute Reservation reservation, Model model) {
        try {
            // Validate required fields
            if (reservation.getCustomerName() == null || reservation.getCustomerName().trim().isEmpty()) {
                throw new RuntimeException("Customer name is required");
            }
            if (reservation.getCustomerEmail() == null || reservation.getCustomerEmail().trim().isEmpty()) {
                throw new RuntimeException("Customer email is required");
            }
            if (reservation.getCustomerPhone() == null || reservation.getCustomerPhone().trim().isEmpty()) {
                throw new RuntimeException("Customer phone is required");
            }
            if (reservation.getTableId() == null) {
                throw new RuntimeException("Table selection is required");
            }
            if (reservation.getReservationDate() == null) {
                throw new RuntimeException("Reservation date is required");
            }
            if (reservation.getReservationTime() == null) {
                throw new RuntimeException("Reservation time is required");
            }
            if (reservation.getNumOfGuests() <= 0) {
                throw new RuntimeException("Number of guests must be greater than 0");
            }

            reservationService.makeReservation(reservation);
            return "redirect:/reservations?success=true";
        } catch (Exception e) {
            model.addAttribute("error", "Error making reservation: " + e.getMessage());
            model.addAttribute("reservation", reservation); // Keep form data
            try {
                model.addAttribute("reservations", reservationService.getAllReservations());
            } catch (Exception ex) {
                model.addAttribute("reservations", new ArrayList<>());
            }
            return "users/reservations";
        }
    }

    // REST API endpoints (separate from web form)
    @PostMapping("/api/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> makeReservationAPI(@RequestBody Reservation reservation) {
        try {
            Reservation createdReservation = reservationService.makeReservation(reservation);
            return ResponseEntity.ok(createdReservation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/api/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> getAllReservationsAPI() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/api/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(reservationService.getReservationById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/api/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Reservation> updateReservation(
            @PathVariable Long id,
            @RequestBody Reservation reservationDetails) {
        try {
            return ResponseEntity.ok(reservationService.updateReservation(id, reservationDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/api/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        try {
            reservationService.deleteReservation(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Test endpoint to verify controller is working
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Controller is working!";
    }
}