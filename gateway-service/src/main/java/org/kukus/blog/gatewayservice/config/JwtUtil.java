package org.kukus.blog.gatewayservice.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // Generate JWT token
    public String generateToken(String userId, String username, List<String> roles) {
        return Jwts.builder()
                .subject(userId)
                .claim("username", username)
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, Jwts.SIG.HS512)
                .compact();
    }

    // Validate and parse JWT token
    public Claims validateToken(String token) {
        return Jwts.parser()
                .verifyWith(key)  // обязательно каст к SecretKey
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Extract user ID from token
    public String getUserIdFromToken(String token) {
        return validateToken(token).getSubject();
    }

    // Extract roles from token
    public List<String> getRolesFromToken(String token) {
        return validateToken(token).get("roles", List.class);
    }
}
