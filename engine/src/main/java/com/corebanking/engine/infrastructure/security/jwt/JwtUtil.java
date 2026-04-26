package com.corebanking.engine.infrastructure.security.jwt;

import com.corebanking.engine.infrastructure.persistence.jpa.entity.UserJpaEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET =
            "my-super-secret-key-my-super-secret-key-my-super-secret-key";

    private final SecretKey key =
            Keys.hmacShaKeyFor(SECRET.getBytes());

    // ================= GENERATE TOKEN =================
    public String generateToken(UserJpaEntity user) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        claims.put("customerId", user.getCustomerId());

        return Jwts.builder()
                .setClaims(claims)

                // ✅ FIX: use EMAIL (NOT ID)
                .setSubject(user.getEmail())

                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key)
                .compact();
    }

    // ================= EXTRACT EMAIL =================
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // ================= EXTRACT ROLE =================
    public String extractRole(String token) {
        return (String) extractAllClaims(token).get("role");
    }

    // ================= EXTRACT CUSTOMER ID =================
    public String extractCustomerId(String token) {
        return (String) extractAllClaims(token).get("customerId");
    }

    // ================= VALIDATE TOKEN =================
    public boolean validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // ================= INTERNAL =================
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}