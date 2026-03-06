package com.corebanking.engine.infrastructure.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET =
            "my-super-secret-key-my-super-secret-key-my-super-secret-key";

    private final SecretKey key =
            Keys.hmacShaKeyFor(SECRET.getBytes());

    // Generate Token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key)
                .compact();
    }

    // Extract Username
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Validate Token
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()     // IMPORTANT
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}