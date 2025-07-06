package com.hotelmanagement.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    final long EXPIRATION_DATE = 1000*60*30;
    final String SECRET_KEY = "ThisIsASecretKeyThisIsASecretKeyThisIsASecretKey";

    public String generateToken(String username) {

        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE))
                .signWith(getKey())
                .compact();
    }

    public SecretKey getKey() {
        byte[] keys = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keys);

    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUserName(String token) {
        Claims claims = extractClaims(token);
        return claims.getSubject();
    }

    public Date extractExpiry(String token) {
        Claims claims = extractClaims(token);
        return claims.getExpiration();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && extractExpiry(token).after(new Date(System.currentTimeMillis())));

    }

}
