package com.agri.agri_management.transaction.service;

import com.agri.agri_management.transaction.entity.Transaction;
import com.agri.agri_management.transaction.entity.TransactionType;
import com.agri.agri_management.transaction.repository.TransactionRepository;
import com.agri.agri_management.product.entity.Product;
import com.agri.agri_management.auth.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepo;

    public void record(Product product, User user, TransactionType type, int quantity, String note) {

        Transaction tx = new Transaction();

        tx.setProductId(product.getProductId());
        tx.setProductName(product.getName());

        tx.setUserId(user.getUserId());
        tx.setUserName(user.getName());

        // 🔥 NEW DATA
        tx.setUserEmail(user.getEmail());
        tx.setUserRole(user.getRole() != null ? user.getRole().name() : "UNKNOWN");

        tx.setTransactionType(type);
        tx.setQuantity(quantity);
        tx.setPrice(product.getPrice());

        tx.setTransactionDate(LocalDateTime.now());
        tx.setNote(note);

        // 🔥 STATUS
        tx.setStatus("SUCCESS");

        transactionRepo.save(tx);
    }

    public List<Transaction> getAll() {
        return transactionRepo.findAllByOrderByTransactionDateDesc();
    }

    public List<Transaction> getByUser(Long userId) {
        return transactionRepo.findByUserIdOrderByTransactionDateDesc(userId);
    }

    public List<Transaction> getByProduct(Long productId) {
        return transactionRepo.findByProductId(productId);
    }

    public List<Transaction> getByType(TransactionType type) {
        return transactionRepo.findByTransactionType(type);
    }
}