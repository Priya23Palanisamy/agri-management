package com.agri.agri_management.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.agri.agri_management.auth.entity.FarmerRequest;
import com.agri.agri_management.auth.entity.RequestStatus;

public interface FarmerRequestRepository extends JpaRepository<FarmerRequest, Long> {
	List<FarmerRequest> findByStatus(RequestStatus status);
}