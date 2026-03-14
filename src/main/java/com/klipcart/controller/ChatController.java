package com.klipcart.controller;

import com.klipcart.model.ChatMessage;
import com.klipcart.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin("*")
public class ChatController {

    @Autowired
    private ChatRepository chatRepository;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody ChatMessage message) {
        try {
            ChatMessage savedMessage = chatRepository.save(message);
            return ResponseEntity.ok(savedMessage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send message");
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<ChatMessage>> getChatHistory(@PathVariable String email) {
        return ResponseEntity.ok(chatRepository.findByCustomerEmailOrderByTimestampAsc(email));
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<ChatMessage>> getAllChatsForAdmin() {
        // Fetching all chats. Frontend will group them by email.
        return ResponseEntity.ok(chatRepository.findAll());
    }
}