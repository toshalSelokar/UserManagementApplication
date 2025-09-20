package com.example.SpringBootDemo.controller;

import com.example.SpringBootDemo.service.KafkaConsumerService;
import com.example.SpringBootDemo.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Kafka Controller
 * 
 * REST endpoints for testing Kafka integration
 * 
 * @author User Management Team
 * @version 1.0
 */
@RestController
@RequestMapping("/api/kafka")
@CrossOrigin(origins = "*")
public class KafkaController {

    private final KafkaProducerService kafkaProducerService;
    private final KafkaConsumerService kafkaConsumerService;

    @Autowired
    public KafkaController(KafkaProducerService kafkaProducerService, 
                          KafkaConsumerService kafkaConsumerService) {
        this.kafkaProducerService = kafkaProducerService;
        this.kafkaConsumerService = kafkaConsumerService;
    }

    /**
     * Send a user event to Kafka
     * POST /api/kafka/user-event
     */
    @PostMapping("/user-event")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> sendUserEvent(@RequestBody Map<String, String> request) {
        String eventType = request.getOrDefault("eventType", "USER_ACTION");
        String userId = request.getOrDefault("userId", "unknown");
        String userDetails = request.getOrDefault("userDetails", "No details provided");

        kafkaProducerService.sendUserEvent(eventType, userId, userDetails);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "User event sent to Kafka successfully");
        response.put("eventType", eventType);
        response.put("userId", userId);
        response.put("topic", "user-events");
        response.put("status", "SENT");

        return ResponseEntity.ok(response);
    }

    /**
     * Send a notification to Kafka
     * POST /api/kafka/notification
     */
    @PostMapping("/notification")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> sendNotification(@RequestBody Map<String, String> request) {
        String recipient = request.getOrDefault("recipient", "unknown@example.com");
        String subject = request.getOrDefault("subject", "Test Notification");
        String content = request.getOrDefault("content", "This is a test notification from Kafka");

        kafkaProducerService.sendNotification(recipient, subject, content);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Notification sent to Kafka successfully");
        response.put("recipient", recipient);
        response.put("subject", subject);
        response.put("topic", "notifications");
        response.put("status", "SENT");

        return ResponseEntity.ok(response);
    }

    /**
     * Send a custom message to any topic
     * POST /api/kafka/custom-message
     */
    @PostMapping("/custom-message")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> sendCustomMessage(@RequestBody Map<String, String> request) {
        String topic = request.getOrDefault("topic", "user-events");
        String key = request.getOrDefault("key", "test-key");
        String message = request.getOrDefault("message", "Test message from API");

        kafkaProducerService.sendMessage(topic, key, message);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Custom message sent to Kafka successfully");
        response.put("topic", topic);
        response.put("key", key);
        response.put("content", message);
        response.put("status", "SENT");

        return ResponseEntity.ok(response);
    }

    /**
     * Get consumed user events (for testing)
     * GET /api/kafka/consumed/user-events
     */
    @GetMapping("/consumed/user-events")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> getConsumedUserEvents() {
        List<String> events = kafkaConsumerService.getConsumedUserEvents();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Retrieved consumed user events");
        response.put("count", events.size());
        response.put("events", events);
        response.put("topic", "user-events");

        return ResponseEntity.ok(response);
    }

    /**
     * Get consumed notifications (for testing)
     * GET /api/kafka/consumed/notifications
     */
    @GetMapping("/consumed/notifications")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> getConsumedNotifications() {
        List<String> notifications = kafkaConsumerService.getConsumedNotifications();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Retrieved consumed notifications");
        response.put("count", notifications.size());
        response.put("notifications", notifications);
        response.put("topic", "notifications");

        return ResponseEntity.ok(response);
    }

    /**
     * Clear all consumed messages (for testing)
     * DELETE /api/kafka/consumed/clear
     */
    @DeleteMapping("/consumed/clear")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> clearConsumedMessages() {
        kafkaConsumerService.clearConsumedMessages();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "All consumed messages cleared successfully");
        response.put("status", "CLEARED");

        return ResponseEntity.ok(response);
    }

    /**
     * Kafka health check
     * GET /api/kafka/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> kafkaHealthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Kafka integration is active");
        response.put("status", "HEALTHY");
        response.put("topics", List.of("user-events", "notifications"));
        response.put("producer", "ACTIVE");
        response.put("consumer", "ACTIVE");

        return ResponseEntity.ok(response);
    }
}
