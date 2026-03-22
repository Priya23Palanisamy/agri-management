package com.agri.agri_management.auth.config;

import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                // ✅ allow ALL frontend pages
                .requestMatchers("/*").permitAll()

                // ✅ allow APIs
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/admin/**").permitAll()

                // allow static files
                .requestMatchers("/css/**", "/js/**").permitAll()

                // everything else
                .anyRequest().permitAll()   // 🔥 TEMPORARY FIX
            );

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}