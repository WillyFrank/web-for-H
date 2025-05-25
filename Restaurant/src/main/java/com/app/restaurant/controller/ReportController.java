package com.app.restaurant.controller;
import com.app.restaurant.model.Reservation;
import com.app.restaurant.service.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/manager")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/report")
    public String showReportForm(Model model) {
        // Show empty form initially (no reservations)
        return "manager/report";
    }

    @PostMapping("/report")
    public String generateReport(
            @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            Model model) {

        List<Reservation> reservations = reportService.getReservationsBetweenDates(fromDate, toDate);

        model.addAttribute("reservations", reservations);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);

        return "manager/report";
    }
}
