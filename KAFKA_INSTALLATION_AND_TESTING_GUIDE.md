# 📨 Kafka Installation and Testing Guide

## 🎯 Overview

This guide will help you install Apache Kafka, integrate it with your Spring Boot project, and test the integration using API endpoints.

## 📋 Prerequisites

- Java 21 (already installed)
- Your Spring Boot application
- Terminal/Command Prompt access

## 🔧 Step 1: Install Apache Kafka

### Option A: Download and Install Manually (Recommended)

1. **Download Kafka**
   ```bash
   # Create a directory for Kafka
   mkdir ~/kafka
   cd ~/kafka
   
   # Download Kafka (replace with latest version)
   curl -O https://downloads.apache.org/kafka/2.8.2/kafka_2.13-2.8.2.tgz
   
   # Extract the archive
   tar -xzf kafka_2.13-2.8.2.tgz
   cd kafka_2.13-2.8.2
   ```

### Option B: Using Homebrew (macOS)

```bash
# Install Kafka using Homebrew
brew install kafka

# Kafka will be installed in /opt/homebrew/bin/
```

### Option C: Using Docker (Alternative)

```bash
# Create docker-compose.yml
version: '3'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:latest
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000
    ports:
      - 2181:2181

  kafka:
    image: confluentinc/cp-kafka:latest
    depends_on:
      - zookeeper
    ports:
      - 9092:9092
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1

# Run with: docker-compose up -d
```

## 🚀 Step 2: Start Kafka Server

### If you installed manually:

1. **Start Zookeeper** (in terminal 1):
   ```bash
   cd ~/kafka/kafka_2.13-2.8.2
   bin/zookeeper-server-start.sh config/zookeeper.properties
   ```

2. **Start Kafka Server** (in terminal 2):
   ```bash
   cd ~/kafka/kafka_2.13-2.8.2
   bin/kafka-server-start.sh config/server.properties
   ```

### If you used Homebrew:

1. **Start Zookeeper**:
   ```bash
   brew services start zookeeper
   ```

2. **Start Kafka**:
   ```bash
   brew services start kafka
   ```

### Verify Kafka is Running:

```bash
# Check if Kafka is listening on port 9092
netstat -an | grep 9092

# Or use telnet
telnet localhost 9092
```

## 🔗 Step 3: Project Integration (Already Done!)

The following has already been implemented in your project:

### ✅ Dependencies Added
```gradle
implementation 'org.springframework.kafka:spring-kafka'
testImplementation 'org.springframework.kafka:spring-kafka-test'
```

### ✅ Configuration Added
```properties
# Kafka Configuration in application.properties
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=user-management-group
spring.kafka.consumer.auto-offset-reset=earliest
```

### ✅ Code Implementation
- **KafkaConfig.java** - Topic configuration
- **KafkaProducerService.java** - Message sending
- **KafkaConsumerService.java** - Message consuming
- **KafkaController.java** - REST endpoints
- **UserService.java** - Integrated with Kafka events

## 🧪 Step 4: Test Kafka Integration

### 4.1 Start Your Spring Boot Application

```bash
cd "/path/to/UserManagementApplication"
./gradlew bootRun
```

### 4.2 Import Updated Postman Collection

The collection now includes a **"📨 Kafka Integration Testing"** section with these endpoints:

1. **Kafka Health Check** - Verify integration
2. **Send User Event** - Test message production
3. **Send Notification** - Test notification system
4. **Send Custom Message** - Test custom topics
5. **Get Consumed Events** - View consumed messages
6. **Test User Creation** - End-to-end testing
7. **Clear Messages** - Cleanup for testing

### 4.3 Testing Sequence

Run these tests **in order**:

#### Test 1: Health Check
```
GET /api/kafka/health
Expected: Status 200, Kafka is HEALTHY
```

#### Test 2: Send User Event
```
POST /api/kafka/user-event
Body: {
  "eventType": "USER_LOGIN",
  "userId": "123",
  "userDetails": "User logged in from test"
}
Expected: Message sent to user-events topic
```

#### Test 3: Send Notification
```
POST /api/kafka/notification
Body: {
  "recipient": "test@example.com",
  "subject": "Test Notification",
  "content": "This is a test notification"
}
Expected: Message sent to notifications topic
```

#### Test 4: Check Consumed Messages
```
GET /api/kafka/consumed/user-events
GET /api/kafka/consumed/notifications
Expected: See the messages you sent
```

#### Test 5: End-to-End Test
```
POST /api/users (Create a user)
Expected: Automatic Kafka events triggered
```

## 📊 What to Look For in Logs

### Application Startup:
```
INFO  - Kafka producer started
INFO  - Kafka consumer started
INFO  - Created topic: user-events
INFO  - Created topic: notifications
```

### When Sending Messages:
```
INFO  - 📤 Sending user event to Kafka: {"eventType":"USER_LOGIN"...}
INFO  - ✅ User event sent successfully with offset: 0
INFO  - 📥 Received user event from topic: user-events, partition: 0, offset: 0
INFO  - 🔄 Processing user event: {"eventType":"USER_LOGIN"...}
INFO  - ✅ User event processed successfully
```

### When Creating Users:
```
INFO  - 📤 Sending user event to Kafka: {"eventType":"USER_CREATED"...}
INFO  - 📤 Sending notification to Kafka: {"recipient":"user@example.com"...}
INFO  - 📥 Received user event from topic: user-events...
INFO  - 📥 Received notification from topic: notifications...
```

## 🔍 Kafka Command Line Tools (Optional)

### List Topics:
```bash
cd ~/kafka/kafka_2.13-2.8.2
bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

### Create Topic Manually:
```bash
bin/kafka-topics.sh --create --topic test-topic --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
```

### Send Message from Command Line:
```bash
bin/kafka-console-producer.sh --topic user-events --bootstrap-server localhost:9092
```

### Consume Messages from Command Line:
```bash
bin/kafka-console-consumer.sh --topic user-events --from-beginning --bootstrap-server localhost:9092
```

## 🚨 Troubleshooting

### Problem: Kafka Connection Refused
**Solution:**
1. Check if Kafka is running: `netstat -an | grep 9092`
2. Verify Zookeeper is running: `netstat -an | grep 2181`
3. Check application.properties has correct bootstrap-servers

### Problem: Topics Not Created
**Solution:**
1. Check Kafka logs for errors
2. Verify auto.create.topics.enable=true in server.properties
3. Create topics manually using command line tools

### Problem: Messages Not Being Consumed
**Solution:**
1. Check consumer group configuration
2. Verify @KafkaListener annotations
3. Check application logs for consumer errors

### Problem: Application Won't Start
**Solution:**
1. Ensure Kafka is running before starting the application
2. Check if port 9092 is available
3. Verify Kafka configuration in application.properties

## 🎯 Testing Checklist

Before reporting issues, verify:

- [ ] Zookeeper is running on port 2181
- [ ] Kafka is running on port 9092
- [ ] Spring Boot application started successfully
- [ ] Topics are created (user-events, notifications)
- [ ] Authentication is working (admin@example.com / admin123)
- [ ] Application logs show Kafka producer/consumer messages

## 🔄 Stop Kafka Services

### Manual Installation:
```bash
# Stop Kafka server (Ctrl+C in the terminal)
# Stop Zookeeper (Ctrl+C in the terminal)
```

### Homebrew:
```bash
brew services stop kafka
brew services stop zookeeper
```

### Docker:
```bash
docker-compose down
```

## 🎉 Success Indicators

You'll know Kafka integration is working when you see:

1. ✅ **Health check returns HEALTHY status**
2. ✅ **Messages appear in application logs**
3. ✅ **Producer sends messages successfully**
4. ✅ **Consumer receives and processes messages**
5. ✅ **User operations trigger automatic Kafka events**
6. ✅ **Consumed messages can be retrieved via API**

## 📚 Next Steps

Once basic integration is working, you can:

1. **Add more topics** for different event types
2. **Implement message serialization** with JSON or Avro
3. **Add error handling** and dead letter queues
4. **Scale consumers** with multiple instances
5. **Add monitoring** with Kafka metrics
6. **Implement event sourcing** patterns

## 🔗 Useful Resources

- [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
- [Spring Kafka Documentation](https://docs.spring.io/spring-kafka/docs/current/reference/html/)
- [Kafka Quick Start](https://kafka.apache.org/quickstart)

---

**Happy Kafka Testing! 📨✨**

*Remember: Kafka is a distributed streaming platform, so even this simple setup demonstrates powerful event-driven architecture concepts!*
