package com.agri.agri_management.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map; // 🔥 IMPORTANT IMPORT

import com.agri.agri_management.auth.entity.FarmerRequest;
import com.agri.agri_management.auth.entity.User;
import com.agri.agri_management.auth.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // ✅ APPROVE FARMER
    @PostMapping("/approve/{id}")
    public String approve(@PathVariable Long id) {
        return adminService.approveFarmer(id);
    }

    // ✅ UNLOCK USER
    @PostMapping("/unlock/{id}")
    public String unlock(@PathVariable Long id) {
        return adminService.unlockUser(id);
    }

    // ✅ GET PENDING REQUESTS
    @GetMapping("/requests")
    public List<FarmerRequest> getRequests() {
        return adminService.getAllRequests();
    }

    // ✅ USER STATS (🔥 NEW FEATURE)
    @GetMapping("/stats")
    public Map<String, Long> getStats() {
        return adminService.getUserStats();
    }

    // ✅ DELETE REQUEST
    @DeleteMapping("/request/{id}")
    public String deleteRequest(@PathVariable Long id) {
        return adminService.deleteRequest(id);
    }

    // ✅ GET LOCKED USERS
    @GetMapping("/locked-users")
    public List<User> getLockedUsers() {
        return adminService.getLockedUsers();
    }

    // ✅ DELETE USER
    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable Long id) {
        return adminService.deleteUser(id);
    }
}