package com.app.restaurant.controller;

import com.app.restaurant.model.ReservationApproval;
import com.app.restaurant.service.ReservationApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class ReservationApprovalController {

    @Autowired
    private ReservationApprovalService service;

    /**
     * List all reservations (for admin)
     */
    @GetMapping("/reservations")
    public String list(Model model) {
        List<ReservationApproval> all = service.getAll();
        model.addAttribute("reservations", all);
        // you’ll need a Thymeleaf template at
        // src/main/resources/templates/manager/reservations.html
        return "manager/reservations";
    }

    /**
     * Show approval form for one reservation
     */
    @GetMapping("/approve/{id}")
    public String showApproveForm(@PathVariable Long id, Model model) {
        ReservationApproval res = service.getById(id);
        model.addAttribute("reservationApproval", res);
        // this must match your form template name:
        // src/main/resources/templates/manager/reservationApproval.html
        return "manager/reservationApproval";
    }

    /**
     * Handle form submission
     */
    @PostMapping("/approve")
    public String processApproval(
            @ModelAttribute("reservationApproval") ReservationApproval reservation) {
        service.updateStatus(
                reservation.getId(),
                reservation.getStatus(),
                reservation.getComments()
        );
        return "redirect:/manager/reservations";
    }


    /**
     * (Optional) Delete a reservation
     */
    @GetMapping("/delet/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/manager/reservations";
    }
}
