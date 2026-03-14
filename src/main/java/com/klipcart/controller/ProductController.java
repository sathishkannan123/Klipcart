package com.klipcart.controller;

import com.klipcart.model.Product;
import com.klipcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("*") 
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @PostMapping
    public Product addProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price,
            @RequestParam(required = false) BigDecimal salePrice,
            @RequestParam Integer stock,
            @RequestParam String sku,
            @RequestParam String category,
            @RequestParam(required = false) String tags,
            @RequestParam MultipartFile image
    ) throws IOException {

        
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setSalePrice(salePrice);
        product.setStock(stock);
        product.setSku(sku);
        product.setCategory(category);
        product.setTags(tags);
        product.setImageUrl(image.getOriginalFilename());

        return repository.save(product);
    }
    @GetMapping
    public List<Product> getAll() {
    return repository.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
    repository.deleteById(id);
    }
    
    @GetMapping("/category/{category}")
    public List<Product> getByCategory(@PathVariable String category){
        return repository.findByCategory(category);
    }
 // பொருளை Update செய்வதற்கான புதிய API
    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price,
            @RequestParam(required = false) BigDecimal salePrice,
            @RequestParam Integer stock,
            @RequestParam String sku,
            @RequestParam String category,
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) MultipartFile image 
    ) throws IOException {

        Product product = repository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setSalePrice(salePrice);
        product.setStock(stock);
        product.setSku(sku);
        product.setCategory(category);
        product.setTags(tags);
        
        if (image != null && !image.isEmpty()) {
            product.setImageUrl(image.getOriginalFilename());
        }
        
        return repository.save(product);
    }
    
}