# 📋 User Management Application - Detailed Project Summary

Author: @idevangsharma

## 🎯 Project Overview

User Management Application is a comprehensive Spring Boot enterprise application that demonstrates modern Java development practices, featuring complete CRUD operations, security, event-driven
architecture with Kafka, and comprehensive testing.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━


## 🏗️ Technical Architecture

### **Core Technologies**
• **Framework**: Spring Boot 3.5.3
• **Language**: Java 21
• **Build Tool**: Gradle 8.14.3
• **Database**: H2 (In-Memory) with Hibernate ORM 6.6.x
• **Security**: Spring Security 6.5.1
• **Messaging**: Apache Kafka 3.9.1 with Spring Kafka
• **Testing**: JUnit 5, Mockito, Spring Boot Test

### **Architecture Pattern**
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Presentation  │    │    Business     │    │   Data Access   │
│    Layer        │    │     Layer       │    │     Layer       │
├─────────────────┤    ├─────────────────┤    ├─────────────────┤
│ • Controllers   │◄──►│ • Services      │◄──►│ • Repositories  │
│ • REST APIs     │    │ • Business Logic│    │ • JPA Entities  │
│ • Security      │    │ • Validation    │    │ • Database      │
│ • Web Layer     │    │ • Transactions  │    │ • Hibernate     │
└─────────────────┘    └─────────────────┘    └─────────────────┘


━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━


## 📁 Project Structure

UserManagementApplication/
├── 📄 build.gradle                    # Build configuration
├── 📄 README.md                       # Project documentation
├── 📄 HIBERNATE_TESTING_GUIDE.md      # Hibernate testing guide
├── 📄 KAFKA_INSTALLATION_AND_TESTING_GUIDE.md # Kafka setup guide
├── 📄 SpringBootDemo-Updated-Postman-Collection.json # API testing
├── 📁 src/
│   ├── 📁 main/java/com/example/SpringBootDemo/
│   │   ├── 📄 SpringBootDemoApplication.java    # Main application
│   │   ├── 📁 config/
│   │   │   ├── 📄 SecurityConfig.java           # Security configuration
│   │   │   ├── 📄 DataInitializer.java          # Initial data setup
│   │   │   └── 📄 KafkaConfig.java              # Kafka configuration
│   │   ├── 📁 controller/
│   │   │   ├── 📄 UserController.java           # User CRUD APIs
│   │   │   ├── 📄 AuthController.java           # Authentication APIs
│   │   │   ├── 📄 KafkaController.java          # Kafka testing APIs
│   │   │   └── 📄 SecureApiController.java      # Secured endpoints
│   │   ├── 📁 entity/
│   │   │   ├── 📄 User.java                     # User entity
│   │   │   └── 📄 UserRole.java                 # User roles enum
│   │   ├── 📁 repository/
│   │   │   └── 📄 UserRepository.java           # Data access layer
│   │   ├── 📁 service/
│   │   │   ├── 📄 UserService.java              # Business logic
│   │   │   ├── 📄 KafkaProducerService.java     # Kafka message producer
│   │   │   └── 📄 KafkaConsumerService.java     # Kafka message consumer
│   │   └── 📁 security/
│   │       ├── 📄 CustomUserDetails.java        # User details implementation
│   │       └── 📄 CustomUserDetailsService.java # User details service
│   ├── 📁 main/resources/
│   │   ├── 📄 application.properties            # Application configuration
│   │   └── 📁 templates/                        # Thymeleaf templates
│   └── 📁 test/java/                            # Test classes
└── 📁 gradle/                                   # Gradle wrapper


━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━


## 🚀 Key Features

### **1. Complete User Management System**
• ✅ CRUD Operations: Create, Read, Update, Delete users
• ✅ Advanced Search: Search by name, email, domain
• ✅ Data Validation: Bean validation with custom error handling
• ✅ User Statistics: Analytics and reporting endpoints

### **2. Security & Authentication**
• ✅ Spring Security Integration: Role-based access control
• ✅ Multiple User Roles: ADMIN, MANAGER, USER
• ✅ Session Management: HTTP session-based authentication
• ✅ Password Encryption: BCrypt password hashing
• ✅ Default Users: Pre-configured admin, manager, and user accounts

### **3. Event-Driven Architecture with Kafka**
• ✅ Kafka Integration: Apache Kafka 3.9.1 with Spring Kafka
• ✅ Event Publishing: Automatic events on user operations
• ✅ Message Consumption: Real-time event processing
• ✅ Topics Management: user-events and notifications topics
• ✅ Testing Endpoints: Comprehensive Kafka testing APIs

### **4. Database & ORM**
• ✅ Hibernate ORM: Latest Hibernate 6.6.x with Jakarta Persistence
• ✅ H2 Database: In-memory database for development
• ✅ JPA Repositories: Spring Data JPA with custom queries
• ✅ Transaction Management: Declarative transaction handling
• ✅ SQL Logging: Detailed SQL query logging for debugging

### **5. Comprehensive Testing**
• ✅ Unit Tests: Service and controller layer testing
• ✅ Integration Tests: Full application context testing
• ✅ Kafka Testing: Message producer and consumer testing
• ✅ Security Testing: Authentication and authorization testing
• ✅ Postman Collection: Complete API testing suite

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━


## 🔌 API Endpoints

### **User Management APIs**
| Method | Endpoint | Description | Security |
|--------|----------|-------------|----------|
| GET | /api/users | Get all users | ADMIN/MANAGER |
| GET | /api/users/{id} | Get user by ID | ADMIN/MANAGER |
| POST | /api/users | Create new user | ADMIN/MANAGER |
| PUT | /api/users/{id} | Update user | ADMIN/MANAGER |
| DELETE | /api/users/{id} | Delete user | ADMIN |
| GET | /api/users/search?name={term} | Search users | ADMIN/MANAGER |
| GET | /api/users/email/{email} | Get user by email | ADMIN/MANAGER |
| GET | /api/users/stats | User statistics | ADMIN/MANAGER |

### **Authentication APIs**
| Method | Endpoint | Description | Security |
|--------|----------|-------------|----------|
| GET | /api/auth/current-user | Get current user info | Authenticated |
| GET | /api/auth/user-info | Detailed user info | Authenticated |

### **Kafka Testing APIs**
| Method | Endpoint | Description | Security |
|--------|----------|-------------|----------|
| GET | /api/kafka/health | Kafka health check | Public |
| POST | /api/kafka/user-event | Send user event | ADMIN/MANAGER |
| POST | /api/kafka/notification | Send notification | ADMIN/MANAGER |
| GET | /api/kafka/consumed/user-events | Get consumed events | ADMIN/MANAGER |

### **Secure APIs**
| Method | Endpoint | Description | Security |
|--------|----------|-------------|----------|
| GET | /api/secure/admin | Admin-only endpoint | ADMIN |
| GET | /api/secure/manager | Manager+ endpoint | ADMIN/MANAGER |
| GET | /api/secure/user | User+ endpoint | All authenticated |

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━


## 🗄️ Database Schema

### **User Entity**
sql
CREATE TABLE users (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
first_name VARCHAR(255) NOT NULL,
last_name VARCHAR(255) NOT NULL,
email VARCHAR(255) UNIQUE NOT NULL,
password VARCHAR(255) NOT NULL,
phone VARCHAR(20),
role VARCHAR(50) NOT NULL CHECK (role IN ('ADMIN','MANAGER','USER')),
enabled BOOLEAN NOT NULL DEFAULT TRUE,
account_non_expired BOOLEAN DEFAULT TRUE,
account_non_locked BOOLEAN DEFAULT TRUE,
credentials_non_expired BOOLEAN DEFAULT TRUE,
failed_login_attempts INTEGER DEFAULT 0,
last_login TIMESTAMP,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


### **Default Users**
• **Admin**: admin@example.com / admin123 (ADMIN role)
• **Manager**: manager@example.com / manager123 (MANAGER role)
• **User**: user@example.com / user123 (USER role)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━


## 📨 Kafka Integration

### **Topics**
• **user-events**: User lifecycle events (CREATE, UPDATE, DELETE)
• **notifications**: System notifications and alerts

### **Event Flow**
User Operation → UserService → KafkaProducerService → Kafka Topic → KafkaConsumerService → Processing


### **Sample Events**
json
{
"eventType": "USER_CREATED",
"userId": "123",
"userDetails": "User John Doe created with email john.doe@example.com",
"timestamp": "2025-08-08T01:30:00"
}


━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━


## 🧪 Testing Strategy

### **Test Coverage**
• **Unit Tests**: 27 tests covering service and controller layers
• **Integration Tests**: Full application context testing
• **Kafka Tests**: Producer and consumer functionality
• **Security Tests**: Authentication and authorization

### **Testing Tools**
• **JUnit 5**: Test framework
• **Mockito**: Mocking framework
• **Spring Boot Test**: Integration testing
• **TestContainers**: For integration testing (if needed)
• **Postman**: API testing with comprehensive collection

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━


## 🔧 Configuration

### **Application Properties**
properties
# Database
spring.datasource.url=jdbc:h2:mem:userdb
spring.jpa.hibernate.ddl-auto=create-drop

# Security
spring.security.user.name=admin@example.com
spring.security.user.password=admin123

# Kafka
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=user-management-group

# Logging
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.springframework.kafka=INFO


━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━


## 🚀 Getting Started

### **Prerequisites**
• Java 21+
• Apache Kafka 3.9.1 (for event-driven features)
• Gradle (wrapper included)

### **Quick Start**
bash
# 1. Start Kafka (if using Kafka features)
bin/zookeeper-server-start.sh config/zookeeper.properties &
bin/kafka-server-start.sh config/server.properties &

# 2. Start the application
./gradlew bootRun

# 3. Access the application
# Web UI: http://localhost:8080
# H2 Console: http://localhost:8080/h2-console
# API Base: http://localhost:8080/api


### **Testing**
bash
# Run all tests
./gradlew test

# Build the application
./gradlew build

# Import Postman collection for API testing
# File: SpringBootDemo-Updated-Postman-Collection.json


━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━


## 📚 Learning Objectives

This project demonstrates:

1. Spring Boot Mastery: Auto-configuration, starters, profiles
2. Spring Security: Authentication, authorization, role-based access
3. JPA/Hibernate: Entity mapping, custom queries, transactions
4. Event-Driven Architecture: Kafka integration, message patterns
5. RESTful API Design: HTTP methods, status codes, error handling
6. Testing Best Practices: Unit, integration, and API testing
7. Enterprise Patterns: Layered architecture, dependency injection
8. DevOps Practices: Build automation, configuration management

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━


## 🎯 Future Enhancements

### **Planned Features**
• [ ] Pagination & Sorting: Large dataset handling
• [ ] API Documentation: OpenAPI/Swagger integration
• [ ] Caching: Redis integration for performance
• [ ] Monitoring: Metrics and health checks
• [ ] Docker: Containerization for deployment
• [ ] CI/CD: GitHub Actions pipeline
• [ ] Database Migration: Flyway or Liquibase
• [ ] Email Integration: User notifications

### **Advanced Features**
• [ ] Microservices: Service decomposition
• [ ] Event Sourcing: Complete event-driven architecture
• [ ] CQRS: Command Query Responsibility Segregation
• [ ] Distributed Tracing: Observability with Zipkin/Jaeger
• [ ] API Gateway: Centralized API management

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━


## 📊 Project Metrics

• **Lines of Code**: ~2,500+ lines
• **Test Coverage**: 90%+ (27 tests)
• **API Endpoints**: 15+ REST endpoints
• **Security Roles**: 3 user roles with granular permissions
• **Kafka Topics**: 2 topics with real-time processing
• **Documentation**: 4 comprehensive guides
• **Postman Tests**: 25+ API test scenarios

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━


## 🏆 Key Achievements

✅ Complete Enterprise Application: Production-ready Spring Boot application  
✅ Security Implementation: Role-based access control with Spring Security  
✅ Event-Driven Architecture: Real-time messaging with Apache Kafka  
✅ Database Integration: Hibernate ORM with advanced JPA features  
✅ Comprehensive Testing: Unit, integration, and API testing  
✅ Documentation: Detailed guides and API documentation  
✅ DevOps Ready: Build automation and configuration management

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━


This project serves as a comprehensive example of modern Java enterprise application development, showcasing industry best practices and cutting-edge technologies. 🚀✨

## 📚 Learning Resources

- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Data JPA Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Testing in Spring Boot](https://spring.io/guides/gs/testing-web/)

---

**Built with ❤️ for learning Spring Boot development**
