package com.agri.agri_management.auth.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.agri.agri_management.auth.entity.*;
import com.agri.agri_management.auth.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner init(UserRepository userRepo) {
        return args -> {

            if(userRepo.findByEmail("admin@gmail.com").isEmpty()) {

                User admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@gmail.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                admin.setStatus(Status.ACTIVE);

                userRepo.save(admin);

                System.out.println("✅ Default Admin Created");
            }
        };
    }
}