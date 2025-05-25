package com.app.restaurant.model;

import javax.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private RestaurantTable table;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String customerName;
    private String customerPhone;
    private String customerEmail;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationDate;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime reservationTime;

    private int numOfGuests;
    private String status;

    // Transient fields for form binding and API
    @Transient
    private Long customerId;

    @Transient
    private Long tableId;

    // Helper method to get tableId from relationship or transient field
    public Long getTableId() {
        if (table != null) {
            return table.getTableId();
        }
        return tableId;
    }

    // Helper method to get customerId from relationship or transient field
   // public Long getCustomerId() {
     //   if (customer != null) {
      //      return customer.getCustomerId();
      //  }
      //  return customerId;
   // }
}