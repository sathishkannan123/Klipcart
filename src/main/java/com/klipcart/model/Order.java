package com.klipcart.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String address;

    private BigDecimal totalAmount;

    private String status; // PENDING, SHIPPED, DELIVERED

    private LocalDateTime orderDate;
    @Column(length = 1000) 
    private String productNames;

	public String getProductNames() {
		return productNames;
	}

	public void setProductNames(String productNames) {
		this.productNames = productNames;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public Order(Long id, String customerName, String customerEmail, String customerPhone, String address,
			BigDecimal totalAmount, String status, LocalDateTime orderDate) {
		super();
		this.id = id;
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.customerPhone = customerPhone;
		this.address = address;
		this.totalAmount = totalAmount;
		this.status = status;
		this.orderDate = orderDate;
	}

	public Order() {
		super();
	}

	
    
    
}