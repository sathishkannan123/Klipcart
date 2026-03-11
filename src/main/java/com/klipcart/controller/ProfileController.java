package com.klipcart.controller;

import com.klipcart.model.User;
import com.klipcart.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin("http://127.0.0.1:5500")
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    // 🌟 Password Encoder-ஐ Inject செய்கிறோம் 🌟
    @Autowired
    private PasswordEncoder passwordEncoder;

    // ==============================
    // 🔹 1. Get Profile Details
    // ==============================
    @GetMapping("/{email}")
    public ResponseEntity<?> getProfile(@PathVariable String email) {

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("User not found!");
        }

        User user = userOptional.get();

        return ResponseEntity.ok(user);
    }

    // ==============================
    // 🔹 2. Update Profile
    // ==============================
    @PutMapping("/update/{email}")
    public ResponseEntity<?> updateProfile(
            @PathVariable String email,
            @RequestBody User updatedData) {

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("User not found!");
        }

        User user = userOptional.get();

        user.setFullName(updatedData.getFullName());
        user.setPhone(updatedData.getPhone());
        user.setAddress(updatedData.getAddress());

        userRepository.save(user);

        return ResponseEntity.ok("Profile updated successfully!");
    }

    // ==============================
    // 🔹 3. Change Password (With BCrypt Security)
    // ==============================
    @PutMapping("/change-password/{email}")
    public ResponseEntity<?> changePassword(
            @PathVariable String email,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("User not found!");
        }

        User user = userOptional.get();

        // 🌟 1. பழைய பாஸ்வேர்ட் சரியாக உள்ளதா என BCrypt மூலம் செக் செய்கிறோம் 🌟
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body("Old password incorrect!");
        }

        // 🌟 2. புதிய பாஸ்வேர்டை Encrypt செய்து சேமிக்கிறோம் 🌟
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.ok("Password changed successfully!");
    }

}