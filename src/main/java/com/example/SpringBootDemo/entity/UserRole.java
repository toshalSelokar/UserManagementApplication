package com.example.SpringBootDemo.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * User roles enum for role-based access control
 * 
 * @author User Management Team
 * @version 1.0
 */
public enum UserRole {
    /**
     * Regular user with basic permissions
     */
    USER("User", "Basic user with limited access"),
    
    /**
     * Manager with elevated permissions
     */
    MANAGER("Manager", "Manager with user management capabilities"),
    
    /**
     * Administrator with full permissions
     */
    ADMIN("Administrator", "Administrator with full system access");
    
    private final String displayName;
    private final String description;
    
    UserRole(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * Get role with ROLE_ prefix for Spring Security
     */
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
    
    /**
     * Get all authorities for this role
     */
    public Set<String> getAuthorities() {
        Set<String> authorities = new HashSet<>();
        authorities.add("ROLE_" + this.name());
        
        // Add role-specific authorities
        switch (this) {
            case ADMIN:
                authorities.add("READ_USERS");
                authorities.add("WRITE_USERS");
                authorities.add("DELETE_USERS");
                authorities.add("ADMIN_ACCESS");
                break;
            case MANAGER:
                authorities.add("READ_USERS");
                authorities.add("WRITE_USERS");
                authorities.add("MANAGER_ACCESS");
                break;
            case USER:
                authorities.add("READ_USERS");
                authorities.add("USER_ACCESS");
                break;
        }
        
        return authorities;
    }
}
