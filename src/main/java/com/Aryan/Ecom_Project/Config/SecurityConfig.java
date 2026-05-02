package com.Aryan.Ecom_Project.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for development if needed
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/products/**", "/api/product/*", "/api/product/*/image").permitAll() // Only allow GET requests publicly
                .anyRequest().authenticated() // Secure everything else (POST, PUT, DELETE)
            )
            .cors(Customizer.withDefaults()) // Enable CORS with default settings (mapped below)
            .oauth2Login(oauth2 -> oauth2
                .defaultSuccessUrl("http://localhost:5173", true) // Redirect to frontend after login
            )
            .logout(logout -> logout
                .logoutSuccessUrl("http://localhost:5173") // Redirect to frontend after logout
                .permitAll()
            );
        
        return http.build();
    }
    
    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
        org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration();
        configuration.setAllowedOrigins(java.util.Arrays.asList("http://localhost:5173", "http://localhost:3000")); // Vite and common React ports
        configuration.setAllowedMethods(java.util.Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(java.util.Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setAllowCredentials(true);
        
        org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
