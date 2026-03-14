package com.klipcart.repository;

import com.klipcart.model.ReturnRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReturnRepository extends JpaRepository<ReturnRequest, Long> {
    List<ReturnRequest> findByCustomerEmail(String customerEmail);
    List<ReturnRequest> findByOrderId(String orderId);
}