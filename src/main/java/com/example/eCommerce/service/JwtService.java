package com.example.eCommerce.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final String secret = "my-secret-key-my-secret-key-my-secret-key-123";

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder().
                setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSecret(), SignatureAlgorithm.HS256)
                .compact();

    }

    private Key getSecret() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(getSecret()).build().parseClaimsJws(token).getBody().getSubject();

    }
}
