package com.app.restaurant.service;

import com.app.restaurant.model.ReservationApproval;
import com.app.restaurant.model.Status;
import com.app.restaurant.repository.ReservationApprovalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationApprovalService {

    @Autowired
    private ReservationApprovalRepository repo;

    public List<ReservationApproval> getAll() {
        return repo.findAll();
    }

    public ReservationApproval getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with ID: " + id));
    }

    public ReservationApproval save(ReservationApproval res) {
        return repo.save(res);
    }

    public void updateStatus(Long id, Status status, String comments) {
        ReservationApproval res = getById(id);
        res.setStatus(status);
        res.setComments(comments);
        repo.save(res);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
