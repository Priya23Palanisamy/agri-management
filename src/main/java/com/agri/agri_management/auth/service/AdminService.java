package com.agri.agri_management.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.agri.agri_management.auth.entity.*;
import com.agri.agri_management.auth.repository.*;

@Service
public class AdminService {

    @Autowired
    private FarmerRequestRepository requestRepo;

    @Autowired
    private UserRepository userRepo;

    // ✅ APPROVE FARMER
    public String approveFarmer(Long id) {

        FarmerRequest req = requestRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (req.getStatus() == RequestStatus.APPROVED) {
            return "Already approved";
        }

        // ✅ Check if user already exists
        if (userRepo.findByEmail(req.getEmail()).isPresent()) {
            return "User already exists";
        }

        // ✅ Create user
        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());
        user.setRole(Role.FARMER);
        user.setStatus(Status.ACTIVE);

        userRepo.save(user);

        // ✅ Update request status
        req.setStatus(RequestStatus.APPROVED);
        requestRepo.save(req);

        return "Farmer approved";
    }

    // ✅ UNLOCK USER
    public String unlockUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus(Status.ACTIVE);
        user.setFailedLoginAttempts(0);

        userRepo.save(user);

        return "User unlocked";
    }

    // ✅ GET ALL PENDING REQUESTS
    public List<FarmerRequest> getAllRequests() {
        return requestRepo.findByStatus(RequestStatus.PENDING);
    }

    // ✅ DELETE REQUEST
    public String deleteRequest(Long id) {
        requestRepo.deleteById(id);
        return "Request deleted";
    }

    // ✅ GET LOCKED USERS
    public List<User> getLockedUsers() {
        return userRepo.findByStatus(Status.LOCKED);
    }

    // ✅ DELETE USER
    public String deleteUser(Long id) {
        userRepo.deleteById(id);
        return "User deleted";
    }

    // ✅ USER STATISTICS (🔥 NEW FEATURE)
    public Map<String, Long> getUserStats() {

        Map<String, Long> stats = new HashMap<>();

        stats.put("totalUsers", userRepo.count());
        stats.put("farmers", userRepo.countByRole(Role.FARMER));
        stats.put("buyers", userRepo.countByRole(Role.BUYER));

        return stats;
    }
}