package com.agri.agri_management.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agri.agri_management.auth.dto.AuthResponse;
import com.agri.agri_management.auth.entity.*;
import com.agri.agri_management.auth.repository.*;
import com.agri.agri_management.auth.security.JwtUtil;
import java.time.LocalDateTime;


@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private FarmerRequestRepository requestRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder; // ✅ FIX 1
    
    @Autowired
    private OtpRepository otpRepo;

    @Autowired
    private EmailService emailService;

    // 👨‍🌾 Farmer Register
    public String registerFarmer(FarmerRequest req) {
        req.setStatus(RequestStatus.PENDING);
        req.setPassword(passwordEncoder.encode(req.getPassword()));
        requestRepo.save(req);
        return "Farmer request submitted";
    }

    // 🛒 Buyer Register
    public String registerBuyer(User user) {
    	System.out.println("NAME FROM FRONTEND: " + user.getName()); // DEBUG
        
    	user.setRole(Role.BUYER);
        user.setStatus(Status.ACTIVE);

        // ✅ FIX 3: encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);
        return "Buyer registered";
    }

    // 🔐 Login
    public AuthResponse login(String email, String password) {

    	User user = userRepo.findByEmail(email)
    	        .orElseThrow(() -> new RuntimeException("User not found"));

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (user.getStatus().equals(Status.LOCKED)) {
            throw new RuntimeException("Account locked");
        }

        // ✅ FIX 1: passwordEncoder used properly
        if (!passwordEncoder.matches(password, user.getPassword())) {

            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);

            if (user.getFailedLoginAttempts() >= 3) {
                user.setStatus(Status.LOCKED);
            }

            userRepo.save(user); // ✅ FIX 2
            throw new RuntimeException("Invalid password");
        }

        // reset attempts
        user.setFailedLoginAttempts(0);
        userRepo.save(user); // ✅ FIX 2

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token, user.getRole().name(),user.getUserId());
    }
    public String sendResetOtp(String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.getRole() == Role.FARMER) {
            throw new RuntimeException("Farmer must contact admin");
        }

        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);

        Otp otpEntity = new Otp();
        otpEntity.setEmail(email);
        otpEntity.setOtp(otp);
        otpEntity.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        otpRepo.save(otpEntity);

        emailService.sendOtp(email, otp);

        return "OTP sent for password reset";
    }
    public String resetPassword(String email, String otpInput, String newPassword) {

        // 1. Get latest OTP
        Otp otp = otpRepo.findTopByEmailOrderByIdDesc(email);

        if (otp == null || !otp.getOtp().equals(otpInput)) {
            throw new RuntimeException("Invalid OTP");
        }

        // 2. Get user
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));


        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // 3. Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);

        // 4. Delete OTP (optional but good)
        otpRepo.delete(otp);

        return "Password reset successful";
    }
}