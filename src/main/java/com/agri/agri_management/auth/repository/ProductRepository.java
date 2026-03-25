package com.agri.agri_management.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.agri.agri_management.auth.entity.Product;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // ✅ Get products by farmer
    List<Product> findByFarmerId(Long farmerId);
    List<Product> findByCategory(String category);
}