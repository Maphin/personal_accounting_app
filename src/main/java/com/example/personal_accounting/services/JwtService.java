package com.example.personal_accounting.services;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expirationTime;

    public String generateToken(Long id, String username) {
        return Jwts.builder()
                .setSubject(username)
                .claim("id", id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    public Long extractUserId(String token) {
        return extractAllClaims(token).get("id", Long.class);
    }
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Claims extractAllClaims(String token) {
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
        return jwtParser
                .parseClaimsJws(token)
                .getBody();
    }
}
