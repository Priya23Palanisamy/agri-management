package com.agri.agri_management.auth.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.agri.agri_management.auth.entity.*;
import com.agri.agri_management.auth.repository.*;

@Configuration
public class DataInitializer {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WarehouseRepository warehouseRepo; // ✅ NEW

    @Bean
    CommandLineRunner init(UserRepository userRepo) {
        return args -> {

            // ✅ 1. Create Default Admin
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

            // ✅ 2. Create Default Warehouses
            if(warehouseRepo.count() == 0) {

                Warehouse w1 = new Warehouse();
                w1.setName("Cold Storage Chennai");
                w1.setLocation("Chennai");
                w1.setCapacity(1000);
                w1.setCurrentStock(0);
                w1.setType("COLD");

                Warehouse w2 = new Warehouse();
                w2.setName("Dry Storage Chennai");
                w2.setLocation("Chennai");
                w2.setCapacity(2000);
                w2.setCurrentStock(0);
                w2.setType("DRY");

                Warehouse w3 = new Warehouse();
                w3.setName("General Storage Chennai");
                w3.setLocation("Chennai");
                w3.setCapacity(1500);
                w3.setCurrentStock(0);
                w3.setType("GENERAL");

                warehouseRepo.save(w1);
                warehouseRepo.save(w2);
                warehouseRepo.save(w3);

                System.out.println("✅ Default Warehouses Created");
            }
        };
    }
}