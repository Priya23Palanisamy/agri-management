package com.agri.agri_management.transaction.repository;

import com.agri.agri_management.transaction.entity.Transaction;
import com.agri.agri_management.transaction.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserId(Long userId);

    List<Transaction> findByProductId(Long productId);

    List<Transaction> findByTransactionType(TransactionType transactionType);

    List<Transaction> findAllByOrderByTransactionDateDesc();

    List<Transaction> findByUserIdOrderByTransactionDateDesc(Long userId);
}