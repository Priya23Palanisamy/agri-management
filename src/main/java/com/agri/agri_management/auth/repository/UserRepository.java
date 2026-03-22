package com.agri.agri_management.auth.repository;
import java.util.List;
import com.agri.agri_management.auth.entity.Status;


import org.springframework.data.jpa.repository.JpaRepository;
import com.agri.agri_management.auth.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByStatus(Status status);
}