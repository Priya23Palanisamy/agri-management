package com.agri.agri_management.alert.repository;

import com.agri.agri_management.alert.entity.Alert;
import java.util.List;
import com.agri.agri_management.alert.entity.AlertStatus;
import com.agri.agri_management.alert.entity.AlertType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    // Check if ACTIVE alert already exists for same product + type
    boolean existsByProductIdAndAlertTypeAndStatus(
            Long productId,
            AlertType alertType,
            AlertStatus status
    );
    List<Alert> findByProductIdAndStatus(Long productId, AlertStatus status);
}
