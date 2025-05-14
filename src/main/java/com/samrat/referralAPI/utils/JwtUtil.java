package com.samrat.referralAPI.utils;

import com.samrat.referralAPI.models.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.security.Key;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "samratsecretkeysamratsecretkey123456"; // should be min 256 bits
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // 1. Generate JWT Token
    public String generateToken(String email, Role role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 2. Extract Email
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // 3. Extract Role
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    // 4. Check token valid or not
    public boolean isTokenValid(String token) {
        try {
            getClaims(token); // will throw if invalid
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // Utility: Get claims from token
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}