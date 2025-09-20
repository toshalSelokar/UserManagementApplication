package com.example.SpringBootDemo.service;

import com.example.SpringBootDemo.entity.User;
import com.example.SpringBootDemo.entity.UserRole;
import com.example.SpringBootDemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for User-related business logic with security features and Kafka integration
 * Demonstrates service layer patterns, transaction management, security integration, and event publishing
 */
@Service
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaProducerService kafkaProducerService;
    
    @Autowired
    public UserService(UserRepository userRepository, 
                      PasswordEncoder passwordEncoder,
                      KafkaProducerService kafkaProducerService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.kafkaProducerService = kafkaProducerService;
    }
    
    /**
     * Create a new user with encoded password
     * @param user the user to create
     * @return the created user with generated ID
     * @throws IllegalArgumentException if email already exists
     */
    public User createUser(User user) {
        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }
        
        // Encode password if provided
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        
        // Set default role if not specified
        if (user.getRole() == null) {
            user.setRole(UserRole.USER);
        }
        
        User savedUser = userRepository.save(user);
        
        // Send Kafka event for user creation
        kafkaProducerService.sendUserEvent("USER_CREATED", 
                savedUser.getId().toString(), 
                String.format("User %s %s created with email %s", 
                        savedUser.getFirstName(), savedUser.getLastName(), savedUser.getEmail()));
        
        // Send welcome notification
        kafkaProducerService.sendNotification(savedUser.getEmail(), 
                "Welcome to User Management System", 
                String.format("Hello %s, your account has been created successfully!", savedUser.getFirstName()));
        
        return savedUser;
    }
    
    /**
     * Get all users
     * @return list of all users
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    /**
     * Get user by ID
     * @param id the user ID
     * @return the user if found
     * @throws RuntimeException if user not found
     */
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    
    /**
     * Get user by email
     * @param email the email address
     * @return the user if found
     * @throws RuntimeException if user not found
     */
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
    
    /**
     * Get optional user by ID
     * @param id the user ID
     * @return Optional containing the user if found
     */
    @Transactional(readOnly = true)
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }
    
    /**
     * Get optional user by email
     * @param email the email address
     * @return Optional containing the user if found
     */
    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    /**
     * Update an existing user
     * @param id the user ID to update
     * @param updatedUser the updated user data
     * @return the updated user
     * @throws RuntimeException if user not found or email conflict
     */
    public User updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        // Check if email is being changed and if new email already exists
        if (!existingUser.getEmail().equals(updatedUser.getEmail()) &&
            userRepository.existsByEmail(updatedUser.getEmail())) {
            throw new IllegalArgumentException("Email " + updatedUser.getEmail() + " is already in use");
        }
        
        // Update fields
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhone(updatedUser.getPhone());
        
        // Update password if provided
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        
        // Update role if provided (only admins should be able to do this)
        if (updatedUser.getRole() != null) {
            existingUser.setRole(updatedUser.getRole());
        }
        
        User savedUser = userRepository.save(existingUser);
        
        // Send Kafka event for user update
        kafkaProducerService.sendUserEvent("USER_UPDATED", 
                savedUser.getId().toString(), 
                String.format("User %s %s updated with email %s", 
                        savedUser.getFirstName(), savedUser.getLastName(), savedUser.getEmail()));
        
        return savedUser;
    }
    
    /**
     * Delete a user by ID
     * @param id the user ID to delete
     * @throws RuntimeException if user not found
     */
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        // Send Kafka event before deletion
        kafkaProducerService.sendUserEvent("USER_DELETED", 
                user.getId().toString(), 
                String.format("User %s %s with email %s was deleted", 
                        user.getFirstName(), user.getLastName(), user.getEmail()));
        
        userRepository.deleteById(id);
    }
    
    /**
     * Search users by name (first name, last name, or full name)
     * @param searchTerm the search term
     * @return list of matching users
     */
    @Transactional(readOnly = true)
    public List<User> searchUsersByName(String searchTerm) {
        return userRepository.findByFullNameContaining(searchTerm);
    }
    
    /**
     * Find users by email domain
     * @param domain the email domain (e.g., "gmail.com")
     * @return list of users with that domain
     */
    @Transactional(readOnly = true)
    public List<User> getUsersByEmailDomain(String domain) {
        return userRepository.findByEmailContaining(domain);
    }
    
    /**
     * Get total count of users
     * @return total number of users
     */
    @Transactional(readOnly = true)
    public long getTotalUserCount() {
        return userRepository.count();
    }
    
    /**
     * Check if user exists by email
     * @param email the email to check
     * @return true if user exists, false otherwise
     */
    @Transactional(readOnly = true)
    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    // Security-related methods
    
    /**
     * Check if the authenticated user owns the resource
     * Used for method-level security
     */
    @Transactional(readOnly = true)
    public boolean isOwner(Long userId, String authenticatedEmail) {
        try {
            User user = getUserById(userId);
            return user.getEmail().equals(authenticatedEmail);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if user can access reports (for managers)
     * Custom authorization logic
     */
    @Transactional(readOnly = true)
    public boolean canAccessReports(String email) {
        try {
            User user = getUserByEmail(email);
            return user.getRole() == UserRole.MANAGER || user.getRole() == UserRole.ADMIN;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get users by role
     */
    @Transactional(readOnly = true)
    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }
    
    /**
     * Enable/disable user account
     */
    public void setUserEnabled(Long userId, boolean enabled) {
        User user = getUserById(userId);
        user.setEnabled(enabled);
        userRepository.save(user);
    }
    
    /**
     * Lock/unlock user account
     */
    public void setUserLocked(Long userId, boolean locked) {
        User user = getUserById(userId);
        user.setAccountNonLocked(!locked);
        userRepository.save(user);
    }
    
    /**
     * Change user password
     */
    public void changePassword(Long userId, String newPassword) {
        User user = getUserById(userId);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
    
    /**
     * Change user role (admin only operation)
     */
    public void changeUserRole(Long userId, UserRole newRole) {
        User user = getUserById(userId);
        user.setRole(newRole);
        userRepository.save(user);
    }
}
