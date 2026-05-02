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
                .requestMatchers("/api/products/**", "/api/product/**/image").permitAll() // Allow public access to products
                .anyRequest().authenticated() // Secure other endpoints
            )
            .oauth2Login(Customizer.withDefaults()); // Enable LinkedIn OAuth2 login
        
        return http.build();
    }
}
