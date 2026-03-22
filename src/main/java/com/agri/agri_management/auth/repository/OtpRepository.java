package com.agri.agri_management.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.agri.agri_management.auth.entity.Otp;

public interface OtpRepository extends JpaRepository<Otp, Long> {
	Otp findTopByEmailOrderByIdDesc(String email);
}