package com.klipcart.controller;

import com.klipcart.model.ReturnRequest;
import com.klipcart.repository.ReturnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/returns")
@CrossOrigin("*")
public class ReturnController {

    @Autowired
    private ReturnRepository returnRepository;

    @PostMapping
    public ResponseEntity<?> createReturnRequest(@RequestBody ReturnRequest returnRequest) {
        try {
            ReturnRequest savedRequest = returnRepository.save(returnRequest);
            return ResponseEntity.ok(savedRequest);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create return request");
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<ReturnRequest>> getReturnsByEmail(@PathVariable String email) {
        return ResponseEntity.ok(returnRepository.findByCustomerEmail(email));
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<ReturnRequest>> getAllReturns() {
        return ResponseEntity.ok(returnRepository.findAll());
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<?> updateReturnStatus(@PathVariable Long id, @RequestParam String status, @RequestParam(required = false) String pickupDate) {
        ReturnRequest request = returnRepository.findById(id).orElse(null);
        if (request != null) {
            request.setStatus(status);
            if (pickupDate != null && !pickupDate.isEmpty()) {
                request.setPickupDate(pickupDate);
            }
            returnRepository.save(request);
            return ResponseEntity.ok("Return status updated successfully");
        }
        return ResponseEntity.notFound().build();
    }
}