package com.agri.agri_management.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import org.springframework.stereotype.Service;

import com.agri.agri_management.auth.entity.*;
import com.agri.agri_management.auth.repository.*;

@Service
public class AdminService {

    @Autowired
    private FarmerRequestRepository requestRepo;

    @Autowired
    private UserRepository userRepo;

    public String approveFarmer(Long id) {

        FarmerRequest req = requestRepo.findById(id).orElseThrow();
        
        if(req.getStatus() == RequestStatus.APPROVED) {
            return "Already approved";
        }

        // ✅ update status
        req.setStatus(RequestStatus.APPROVED);
        requestRepo.save(req);

        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());
        user.setRole(Role.FARMER);
        user.setStatus(Status.ACTIVE);
        
        if(userRepo.findByEmail(req.getEmail()).isPresent()) {
            return "User already exists";
        }

        userRepo.save(user);

        req.setStatus(RequestStatus.APPROVED);
        requestRepo.save(req);

        return "Farmer approved";
    }

    public String unlockUser(Long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        user.setStatus(Status.ACTIVE);
        user.setFailedLoginAttempts(0);
        userRepo.save(user);
        return "User unlocked";
    }
    public List<FarmerRequest> getAllRequests() {
        return requestRepo.findByStatus(RequestStatus.PENDING);
    }
    public String deleteRequest(Long id) {
        requestRepo.deleteById(id);
        return "Request deleted";
    }
    public List<User> getLockedUsers() {
        return userRepo.findByStatus(Status.LOCKED);
    }
    public String deleteUser(Long id) {
        userRepo.deleteById(id);
        return "User deleted";
    }
}