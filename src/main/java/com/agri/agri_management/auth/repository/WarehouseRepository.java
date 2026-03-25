package com.agri.agri_management.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.agri.agri_management.auth.entity.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}