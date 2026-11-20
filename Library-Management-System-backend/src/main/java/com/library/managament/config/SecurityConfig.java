package com.library.managament.config;

import java.util.List;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {
	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Value("${app.cors.allowed-origins}")
	private String allowedOrigins;
	
	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
	    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http)
	            throws Exception {

		 http
		    .csrf(csrf -> csrf.disable())
		    .cors(cors -> {})
		    .sessionManagement(session ->
		        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		    )
	            
	            .authorizeHttpRequests(auth -> auth
	            		
	            		       .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
	            		
	            			    // Anyone can register
	            			    .requestMatchers("/users", "/login", "/health").permitAll()

	            			    // Book read operations
	            			    .requestMatchers(HttpMethod.GET, "/books/**")
	            			    .hasAnyRole("ADMIN", "MEMBER")

	            			    // Book write operations
	            			    .requestMatchers(HttpMethod.POST, "/books")
	            			    .hasRole("ADMIN")

	            			    .requestMatchers(HttpMethod.PUT, "/books/**")
	            			    .hasRole("ADMIN")

	            			    .requestMatchers(HttpMethod.DELETE, "/books/**")
	            			    .hasRole("ADMIN")

	            			    
	            			 // Author read operations
	            			    .requestMatchers(HttpMethod.GET, "/authors/**")
	            			    .hasAnyRole("ADMIN", "MEMBER")

	            			    // Author write operations
	            			    .requestMatchers(HttpMethod.POST, "/authors")
	            			    .hasRole("ADMIN")

	            			    .requestMatchers(HttpMethod.PUT, "/authors/**")
	            			    .hasRole("ADMIN")

	            			    .requestMatchers(HttpMethod.DELETE, "/authors/**")
	            			    .hasRole("ADMIN")
	            			    
	            			 // Member operations
	            			    .requestMatchers("/members/**")
	            			    .hasRole("ADMIN")
	            			    
	            			 // Borrowing - create and return
	            			    .requestMatchers(HttpMethod.POST, "/borrowings")
	            			    .hasAnyRole("ADMIN", "MEMBER")

	            			    .requestMatchers(HttpMethod.PUT, "/borrowings/**")
	            			    .hasAnyRole("ADMIN", "MEMBER")

	            			    // Borrowing - administrative viewing
	            			    .requestMatchers(HttpMethod.GET, "/borrowings/**")
	            			    .hasRole("ADMIN")
	            			    
	            			 // Fine operations
	            			    .requestMatchers("/fines/**")
	            			    .hasRole("ADMIN")
	            			    
	            			    
	            			    // Everything else requires login
	            			    .anyRequest().authenticated()
	            			);
	        
	        http.addFilterBefore(
	                jwtAuthenticationFilter,
	                UsernamePasswordAuthenticationFilter.class
	        );

	        return http.build();
	    }
	
	 @Bean
		public PasswordEncoder passwordEncoder() {
		    return new BCryptPasswordEncoder();
		}
		
	 @Bean
	 public CorsConfigurationSource corsConfigurationSource() {
		 CorsConfiguration configuration = new CorsConfiguration(); 
		 configuration.setAllowedOrigins(Arrays.stream(allowedOrigins.split(","))
				 .map(String::trim)
				 .filter(origin -> !origin.isEmpty())
				 .toList());
		 configuration.setAllowedMethods( List.of( "GET", "POST", "PUT", "DELETE", "OPTIONS" ) ); 
		 configuration.setAllowedHeaders( List.of("*") ); 
		 UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); 
		 source.registerCorsConfiguration( "/**", configuration ); 
		 
		 return source; }
}
