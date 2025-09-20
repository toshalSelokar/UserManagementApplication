package com.example.SpringBootDemo;

import com.example.SpringBootDemo.controller.UserController;
import com.example.SpringBootDemo.service.UserService;
import com.example.SpringBootDemo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test for the main Spring Boot application
 * Verifies that the application context loads successfully
 */
@SpringBootTest
@ActiveProfiles("test")
class SpringBootDemoApplicationTests {

	@Autowired
	private UserController userController;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
		// Verify that the Spring context loads successfully
		// and all our main components are properly wired
		assertThat(userController).isNotNull();
		assertThat(userService).isNotNull();
		assertThat(userRepository).isNotNull();
	}
	
	@Test
	void applicationStartsSuccessfully() {
		// This test verifies that the application can start without errors
		// The @SpringBootTest annotation will start the full application context
		// If there are any configuration issues, this test will fail
		assertThat(true).isTrue(); // Simple assertion to confirm test runs
	}

}
