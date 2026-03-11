package com.klipcart.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.klipcart.model.User;
import com.klipcart.repository.UserRepository;
import com.klipcart.security.JwtUtil;

@RestController
@CrossOrigin("*")
public class Controller {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // 🌟 Password Encryptor

    @Autowired
    private JwtUtil jwtUtil; // 🌟 JWT Tool

    // 🌟 1. Register with Encryption 🌟
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "Email already exists!";
        }
        
        // பாஸ்வேர்டை Encrypt செய்து சேமிக்கிறோம்
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
        
        return "Registration successful!";
    }

    // 🌟 2. Customer Login with JWT 🌟
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        User user = userRepository.findByEmail(loginUser.getEmail()).orElse(null);

        // Encrypt செய்யப்பட்ட பாஸ்வேர்டுடன் ஒப்பிடுகிறோம்
        if (user != null && passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
            
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole()); // Token உருவாக்கம்
            
            // Token மற்றும் User விவரங்களை JSON ஆக அனுப்புகிறோம்
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("fullName", user.getFullName());
            response.put("email", user.getEmail());
            response.put("role", user.getRole());
            
            return ResponseEntity.ok(response);
        }
        
        return ResponseEntity.badRequest().body("Invalid Email or Password!");
    }

    // 🌟 3. Admin Login with JWT 🌟
    @PostMapping("/admin-login")
    public ResponseEntity<?> adminLogin(@RequestBody User loginUser) {
        String adminEmail = "admin@klipcart.com";
        String adminPassword = "Kannan@123"; // நிஜ ப்ராஜெக்ட்டில் இதையும் DB-ல் Encrypt செய்து வைக்க வேண்டும்

        if (adminEmail.equals(loginUser.getEmail()) && adminPassword.equals(loginUser.getPassword())) {
            String token = jwtUtil.generateToken(adminEmail, "ADMIN");
            
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("role", "ADMIN");
            
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body("Invalid Admin Credentials!");
    }

    @GetMapping("/api/admin/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @DeleteMapping("/api/admin/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "User deleted successfully!";
    }
}