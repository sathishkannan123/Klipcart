package com.klipcart.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Data

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 2000)
    private String description;

    private BigDecimal price;
    private BigDecimal salePrice;
    private Integer stock;
    private String sku;
    private String category;
    private String tags;
    private String imageUrl;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Product(Long id, String name, String description, BigDecimal price, BigDecimal salePrice, Integer stock,
			String sku, String category, String tags, String imageUrl) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.salePrice = salePrice;
		this.stock = stock;
		this.sku = sku;
		this.category = category;
		this.tags = tags;
		this.imageUrl = imageUrl;
	}
	public Product() {
		super();
	}
    
    
}