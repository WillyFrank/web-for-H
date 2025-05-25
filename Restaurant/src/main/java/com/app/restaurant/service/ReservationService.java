package com.app.restaurant.service;
import com.app.restaurant.model.Customer;
import com.app.restaurant.model.Reservation;
import com.app.restaurant.model.RestaurantTable;
import com.app.restaurant.repository.CustomerRepository;
import com.app.restaurant.repository.ReservationRepository;
import com.app.restaurant.repository.RestaurantTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestaurantTableRepository tableRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation makeReservation(Reservation reservation) {
        // Find or create customer
        //Customer customer = findOrCreateCustomer(reservation);

        // Find table
        RestaurantTable table = tableRepository.findById(reservation.getTableId())
                .orElseThrow(() -> new RuntimeException("Table not found with ID: " + reservation.getTableId()));

        // Set relationships
        //reservation.setCustomer(customer);
        reservation.setTable(table);
        reservation.setStatus("Pending");

        return reservationRepository.save(reservation);
    }


    public Reservation updateReservation(Long id, Reservation reservationDetails) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        // Find or create customer

        // Find table
        RestaurantTable table = tableRepository.findById(reservationDetails.getTableId())
                .orElseThrow(() -> new RuntimeException("Table not found"));

        // Update reservation
        //reservation.setCustomer(customer);
        reservation.setTable(table);
        reservation.setCustomerName(reservationDetails.getCustomerName());
        reservation.setCustomerPhone(reservationDetails.getCustomerPhone());
        reservation.setCustomerEmail(reservationDetails.getCustomerEmail());
        reservation.setReservationDate(reservationDetails.getReservationDate());
        reservation.setReservationTime(reservationDetails.getReservationTime());
        reservation.setNumOfGuests(reservationDetails.getNumOfGuests());
        if (reservationDetails.getStatus() != null) {
            reservation.setStatus(reservationDetails.getStatus());
        }

        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }
}