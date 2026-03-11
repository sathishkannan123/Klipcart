package com.klipcart.controller;

import com.klipcart.model.Product;
import com.klipcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

        // 1. Upload Folder-ஐ உருவாக்குவதற்கான Path
        String uploadDir = "uploads/";
        Path uploadPath = Paths.get(uploadDir);

        // 2. Folder இல்லையென்றால் புதிதாக உருவாக்குதல்
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 3. File-ஐ Save செய்தல் (Temp folder பிரச்சனை வராது)
        Path filePath = uploadPath.resolve(image.getOriginalFilename());
        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // 4. Product-ஐ Database-ல் Save செய்தல்
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setSalePrice(salePrice);
        product.setStock(stock);
        product.setSku(sku);
        product.setCategory(category);
        product.setTags(tags);
        String imageURL = "uploads/" + image.getOriginalFilename();
        product.setImageUrl(imageURL);

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
            @RequestParam(required = false) MultipartFile image // Image Optional ஆக மாற்றப்பட்டுள்ளது
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

        // புதிய இமேஜ் அப்லோட் செய்யப்பட்டால் மட்டும் அதை மாற்றுகிறோம்
        if (image != null && !image.isEmpty()) {
            String uploadDir = "uploads/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(image.getOriginalFilename());
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            product.setImageUrl("uploads/" + image.getOriginalFilename());
        }

        return repository.save(product);
    }
    
}