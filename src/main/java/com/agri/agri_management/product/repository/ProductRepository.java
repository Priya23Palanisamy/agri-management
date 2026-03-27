package com.agri.agri_management.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.agri.agri_management.product.entity.Product;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    //Get products by farmer
    List<Product> findByCategory(String category);
    List<Product> findByUserUserId(Long userId);
}