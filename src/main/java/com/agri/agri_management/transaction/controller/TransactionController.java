package com.agri.agri_management.transaction.controller;

import com.agri.agri_management.transaction.entity.Transaction;
import com.agri.agri_management.transaction.entity.TransactionType;
import com.agri.agri_management.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@CrossOrigin
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/all")
    public List<Transaction> getAll() {
        return transactionService.getAll();
    }

    @GetMapping("/user")
    public List<Transaction> getByUser(@RequestParam Long userId) {
        return transactionService.getByUser(userId);
    }

    @GetMapping("/product")
    public List<Transaction> getByProduct(@RequestParam Long productId) {
        return transactionService.getByProduct(productId);
    }

    @GetMapping("/type")
    public List<Transaction> getByType(@RequestParam TransactionType type) {
        return transactionService.getByType(type);
    }
}