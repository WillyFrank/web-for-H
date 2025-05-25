package com.app.restaurant.repository;

import com.app.restaurant.model.ReservationApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ReservationApprovalRepository extends JpaRepository<ReservationApproval, Long>{
}
