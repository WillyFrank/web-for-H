package com.app.restaurant.service;
import com.app.restaurant.model.Reservation;
import com.app.restaurant.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportService {
    private final ReservationRepository reservationRepository;

    public List<Reservation> getReservationsBetweenDates(LocalDate fromDate, LocalDate toDate) {
        return reservationRepository.findByReservationDateBetween(fromDate, toDate);
    }
}
