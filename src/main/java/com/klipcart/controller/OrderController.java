package com.klipcart.controller;

import com.klipcart.model.Order;
import com.klipcart.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")

@CrossOrigin("*")
public class OrderController {
    @Autowired
    private OrderRepository repository;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        return repository.save(order);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    @PutMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id,
                              @RequestParam String status) {
        Order order = repository.findById(id).orElseThrow();
        order.setStatus(status);
        return repository.save(order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        repository.deleteById(id);
    }
}