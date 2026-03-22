package com.agri.agri_management.auth.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;


@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;
    
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .signWith(getKey())
                .compact();
    }
    

    public String extractEmail(String token) {

        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}