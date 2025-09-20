package com.example.SpringBootDemo.service;

import com.example.SpringBootDemo.config.KafkaConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

/**
 * Kafka Producer Service
 * 
 * This service handles sending messages to Kafka topics.
 * 
 * @author User Management Team
 * @version 1.0
 */
@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Send user event message to Kafka
     */
    public void sendUserEvent(String eventType, String userId, String userDetails) {
        String message = String.format("{\"eventType\":\"%s\",\"userId\":\"%s\",\"userDetails\":\"%s\",\"timestamp\":\"%s\"}", 
                eventType, userId, userDetails, LocalDateTime.now());
        
        logger.info("📤 Sending user event to Kafka: {}", message);
        
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(KafkaConfig.USER_EVENTS_TOPIC, userId, message);
        
        future.whenComplete((result, exception) -> {
            if (exception == null) {
                logger.info("✅ User event sent successfully: {} with offset: {}", 
                        message, result.getRecordMetadata().offset());
            } else {
                logger.error("❌ Failed to send user event: {}", message, exception);
            }
        });
    }

    /**
     * Send notification message to Kafka
     */
    public void sendNotification(String recipient, String subject, String content) {
        String message = String.format("{\"recipient\":\"%s\",\"subject\":\"%s\",\"content\":\"%s\",\"timestamp\":\"%s\"}", 
                recipient, subject, content, LocalDateTime.now());
        
        logger.info("📤 Sending notification to Kafka: {}", message);
        
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(KafkaConfig.NOTIFICATIONS_TOPIC, recipient, message);
        
        future.whenComplete((result, exception) -> {
            if (exception == null) {
                logger.info("✅ Notification sent successfully: {} with offset: {}", 
                        message, result.getRecordMetadata().offset());
            } else {
                logger.error("❌ Failed to send notification: {}", message, exception);
            }
        });
    }

    /**
     * Send custom message to any topic
     */
    public void sendMessage(String topic, String key, String message) {
        logger.info("📤 Sending custom message to topic {}: {}", topic, message);
        
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, message);
        
        future.whenComplete((result, exception) -> {
            if (exception == null) {
                logger.info("✅ Custom message sent successfully to topic {}: {} with offset: {}", 
                        topic, message, result.getRecordMetadata().offset());
            } else {
                logger.error("❌ Failed to send custom message to topic {}: {}", topic, message, exception);
            }
        });
    }
}
