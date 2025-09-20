package com.example.SpringBootDemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security Configuration
 * 
 * This configuration sets up:
 * - URL-based security
 * - Form-based authentication
 * - Role-based access control
 * - Method-level security
 * - Password encoding
 * 
 * @author User Management Team
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    /**
     * Configure HTTP Security
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Configure URL-based authorization
            .authorizeHttpRequests(authz -> authz
                // Public endpoints - no authentication required
                .requestMatchers("/", "/home", "/public/**").permitAll()
                .requestMatchers("/register", "/login", "/error").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                
                // H2 Console and Actuator (for development)
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/actuator/health").permitAll()
                
                // Admin-only endpoints
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                
                // Manager and Admin endpoints
                .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers("/api/manager/**").hasAnyRole("MANAGER", "ADMIN")
                
                // User endpoints (accessible by authenticated users)
                .requestMatchers("/user/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                .requestMatchers("/profile").authenticated()
                
                // API endpoints - require authentication
                .requestMatchers("/api/users/**").authenticated()
                
                // All other requests require authentication
                .anyRequest().authenticated()
            )
            
            // Configure form-based login
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
                .usernameParameter("email") // Use email as username
                .passwordParameter("password")
                .permitAll()
            )
            
            // Configure logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .permitAll()
            )
            
            // Configure session management
            .sessionManagement(session -> session
                .maximumSessions(1) // Allow only one session per user
                .maxSessionsPreventsLogin(false) // Allow new login to invalidate old session
                .expiredUrl("/login?expired=true")
            )
            
            // Configure exception handling
            .exceptionHandling(exceptions -> exceptions
                .accessDeniedPage("/access-denied")
            )
            
            // CSRF configuration
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**", "/api/**") // Disable CSRF for API and H2
            )
            
            // Headers configuration
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin()) // Allow frames for H2 console
            );

        return http.build();
    }

    /**
     * Password Encoder Bean
     * Uses BCrypt for secure password hashing
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12); // Strength of 12 for good security
    }
}
