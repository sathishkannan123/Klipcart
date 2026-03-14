package com.klipcart.repository;

import com.klipcart.model.CartItem;
import com.klipcart.model.User;
import com.klipcart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartItem, Long> {
   
    List<CartItem> findByUser(User user);
    
    
    Optional<CartItem> findByUserAndProduct(User user, Product product);
}