package com.library.managament.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.library.managament.entity.Role;
import com.library.managament.entity.User;
import com.library.managament.repository.UserRepository;

@Configuration
@ConditionalOnProperty(name = "app.admin.bootstrap-enabled", havingValue = "true")
public class DefaultAdminConfiguration {

	@Bean
	CommandLineRunner seedDefaultAdmin(
			UserRepository userRepository,
			PasswordEncoder passwordEncoder,
			@Value("${ADMIN_USERNAME:Admin}") String username,
			@Value("${ADMIN_PASSWORD}") String password) {
		return args -> {
			if (userRepository.findByUsername(username).isEmpty()) {
				User admin = new User();
				admin.setUsername(username);
				admin.setPassword(passwordEncoder.encode(password));
				admin.setRole(Role.ADMIN);
				userRepository.save(admin);
			}
		};
	}
}
