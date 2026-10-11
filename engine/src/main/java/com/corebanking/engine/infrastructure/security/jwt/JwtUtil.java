package com.corebanking.engine.infrastructure.security.jwt;

import com.corebanking.engine.infrastructure.persistence.jpa.entity.UserJpaEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private final SecretKey key;

    public JwtUtil(@Value("${security.jwt.secret}") String secret) {
        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
        if (bytes.length < 32) {
            throw new IllegalStateException("security.jwt.secret must be at least 32 UTF-8 bytes");
        }
        this.key = Keys.hmacShaKeyFor(bytes);
    }

    public String generateToken(UserJpaEntity user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        claims.put("customerId", user.getCustomerId());
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 3_600_000))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) { return extractAllClaims(token).getSubject(); }
    public String extractRole(String token) { return (String) extractAllClaims(token).get("role"); }
    public String extractCustomerId(String token) { return (String) extractAllClaims(token).get("customerId"); }

    public boolean validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.getExpiration() != null && claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}