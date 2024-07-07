package com.example.providers.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtUtilService {

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60 * 8; // 8 Hours

    /* Extract username from token */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /* Extract expiration date from token */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /* Extract a specific claim from token */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    /* Extract all claims from token */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
    }

    /* Check if the token is expired */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /* Generate token */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        addRolesToClaims(claims, userDetails);
        return createToken(claims, userDetails.getUsername());
    }

    /* Add roles to claims */
    private void addRolesToClaims(Map<String, Object> claims, UserDetails userDetails) {
        String roles = userDetails.getAuthorities().stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
        claims.put("roles", roles);
    }

    /* Create token with claims and subject */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SECRET_KEY)
                .compact();
    }

    /* Validate token */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
