package com.example.SpringBootDemo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Kafka Configuration
 * 
 * This class configures Kafka topics and other Kafka-related settings.
 * 
 * @author User Management Team
 * @version 1.0
 */
@Configuration
public class KafkaConfig {

    public static final String USER_EVENTS_TOPIC = "user-events";
    public static final String NOTIFICATIONS_TOPIC = "notifications";

    /**
     * Create user-events topic
     * This topic will be used for user-related events (create, update, delete)
     */
    @Bean
    public NewTopic userEventsTopic() {
        return TopicBuilder.name(USER_EVENTS_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

    /**
     * Create notifications topic
     * This topic will be used for sending notifications
     */
    @Bean
    public NewTopic notificationsTopic() {
        return TopicBuilder.name(NOTIFICATIONS_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
