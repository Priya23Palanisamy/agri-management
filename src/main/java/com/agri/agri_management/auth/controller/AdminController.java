package com.agri.agri_management.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import com.agri.agri_management.auth.service.AuthService;
import com.agri.agri_management.auth.entity.FarmerRequest;
import com.agri.agri_management.auth.entity.User;

import org.springframework.web.bind.annotation.*;

import com.agri.agri_management.auth.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/approve/{id}")
    public String approve(@PathVariable Long id) {
        return adminService.approveFarmer(id);
    }

    @PostMapping("/unlock/{id}")
    public String unlock(@PathVariable Long id) {
        return adminService.unlockUser(id);
    }
    @GetMapping("/requests")
    public List<FarmerRequest> getRequests() {
        return adminService.getAllRequests();
    }
 // 📌 Delete request
    @DeleteMapping("/request/{id}")
    public String deleteRequest(@PathVariable Long id) {
        return adminService.deleteRequest(id);
    }

    // 📌 Get locked users
    @GetMapping("/locked-users")
    public List<User> getLockedUsers() {
        return adminService.getLockedUsers();
    }

    // 📌 Delete user
    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable Long id) {
        return adminService.deleteUser(id);
    }
}