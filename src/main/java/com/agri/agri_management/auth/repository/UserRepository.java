package com.agri.agri_management.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agri.agri_management.auth.entity.User;
import com.agri.agri_management.auth.entity.Status;
import com.agri.agri_management.auth.entity.Role; // 🔥 IMPORTANT IMPORT

public interface UserRepository extends JpaRepository<User, Long> {
	
	

    Optional<User> findByEmail(String email);

    List<User> findByStatus(Status status);

    // 🔥 COUNT METHODS FOR ADMIN DASHBOARD
    long countByRole(Role role);

    long count();
}