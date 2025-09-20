package com.example.SpringBootDemo.controller;

import com.example.SpringBootDemo.entity.User;
import com.example.SpringBootDemo.security.CustomUserDetails;
import com.example.SpringBootDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Secure API Controller demonstrating method-level security
 * 
 * This controller shows different authorization patterns:
 * - Role-based access control
 * - Authority-based access control
 * - Custom authorization logic
 * 
 * @author User Management Team
 * @version 1.0
 */
@RestController
@RequestMapping("/api/secure")
public class SecureApiController {

    @Autowired
    private UserService userService;

    /**
     * Get current user information
     * Available to all authenticated users
     */
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Object>> getCurrentUser(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        
        Map<String, Object> response = new HashMap<>();
        response.put("user", userDetails.toUser());
        response.put("authorities", userDetails.getAuthorities());
        response.put("role", userDetails.getRole());
        response.put("message", "Current user information retrieved successfully");
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get all users - Admin only
     */
    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getAllUsers(Authentication auth) {
        List<User> users = userService.getAllUsers();
        
        Map<String, Object> response = new HashMap<>();
        response.put("users", users);
        response.put("count", users.size());
        response.put("requestedBy", auth.getName());
        response.put("message", "All users retrieved successfully");
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get user statistics - Admin and Manager only
     */
    @GetMapping("/admin/stats")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> getUserStats(Authentication auth) {
        List<User> users = userService.getAllUsers();
        
        long adminCount = users.stream().filter(u -> u.getRole().name().equals("ADMIN")).count();
        long managerCount = users.stream().filter(u -> u.getRole().name().equals("MANAGER")).count();
        long userCount = users.stream().filter(u -> u.getRole().name().equals("USER")).count();
        long enabledCount = users.stream().filter(User::isEnabled).count();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", users.size());
        stats.put("adminUsers", adminCount);
        stats.put("managerUsers", managerCount);
        stats.put("regularUsers", userCount);
        stats.put("enabledUsers", enabledCount);
        stats.put("disabledUsers", users.size() - enabledCount);
        
        Map<String, Object> response = new HashMap<>();
        response.put("statistics", stats);
        response.put("requestedBy", auth.getName());
        response.put("message", "User statistics retrieved successfully");
        
        return ResponseEntity.ok(response);
    }

    /**
     * Create user - Admin only
     */
    @PostMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody User user, Authentication auth) {
        User createdUser = userService.createUser(user);
        
        Map<String, Object> response = new HashMap<>();
        response.put("user", createdUser);
        response.put("createdBy", auth.getName());
        response.put("message", "User created successfully");
        
        return ResponseEntity.ok(response);
    }

    /**
     * Delete user - Admin only
     */
    @DeleteMapping("/admin/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id, Authentication auth) {
        userService.deleteUser(id);
        
        Map<String, Object> response = new HashMap<>();
        response.put("deletedUserId", id);
        response.put("deletedBy", auth.getName());
        response.put("message", "User deleted successfully");
        
        return ResponseEntity.ok(response);
    }

    /**
     * Update user - Users can update their own profile, Admins can update any profile
     */
    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.isOwner(#id, authentication.name)")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, 
                                                          @RequestBody User user, 
                                                          Authentication auth) {
        User updatedUser = userService.updateUser(id, user);
        
        Map<String, Object> response = new HashMap<>();
        response.put("user", updatedUser);
        response.put("updatedBy", auth.getName());
        response.put("message", "User updated successfully");
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get user by ID - Users can view their own profile, Admins and Managers can view any profile
     */
    @GetMapping("/users/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or @userService.isOwner(#id, authentication.name)")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id, Authentication auth) {
        User user = userService.getUserById(id);
        
        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("requestedBy", auth.getName());
        response.put("message", "User retrieved successfully");
        
        return ResponseEntity.ok(response);
    }

    /**
     * Manager reports - Manager and Admin only
     */
    @GetMapping("/manager/reports")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> getManagerReports(Authentication auth) {
        List<User> users = userService.getAllUsers();
        
        Map<String, Object> reports = new HashMap<>();
        reports.put("totalUsers", users.size());
        reports.put("recentUsers", users.stream()
                .filter(u -> u.getCreatedAt().isAfter(java.time.LocalDateTime.now().minusDays(30)))
                .count());
        reports.put("activeUsers", users.stream().filter(User::isEnabled).count());
        
        Map<String, Object> response = new HashMap<>();
        response.put("reports", reports);
        response.put("generatedBy", auth.getName());
        response.put("generatedAt", java.time.LocalDateTime.now());
        response.put("message", "Manager reports generated successfully");
        
        return ResponseEntity.ok(response);
    }

    /**
     * Test authorization endpoint - demonstrates complex authorization
     */
    @GetMapping("/test/authorization")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('MANAGER') and @userService.canAccessReports(authentication.name))")
    public ResponseEntity<Map<String, Object>> testAuthorization(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Authorization test successful!");
        response.put("user", userDetails.getUsername());
        response.put("role", userDetails.getRole());
        response.put("authorities", userDetails.getAuthorities());
        response.put("testResult", "PASSED");
        
        return ResponseEntity.ok(response);
    }
}
