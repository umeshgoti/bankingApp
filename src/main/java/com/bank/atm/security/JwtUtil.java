package com.bank.atm.security;

import com.bank.atm.service.impl.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final String secret = "U5B6tdutFtRnIbRdZIFO-jU2cIzyjNR4Xc1VVAQGTi8asjhdbflhalhbdfLjhblHLBHBlhbvlhbLJBhbvlkhBBVH";
    String base64Secret = Base64.getEncoder().encodeToString(secret.getBytes());
    private final Long expiration = 60 * 60 * 1000l;

    public String extractUsername(String token) {
        return extractClaim(token, claims -> claims.get("mobile", String.class));
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("Role", String.class));
    }

    public String extractEmail(String token) {
        return extractClaim(token, claims -> claims.get("Email", String.class));
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(base64Secret.getBytes());
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (io.jsonwebtoken.SignatureException e) {
            throw new RuntimeException("Invalid JWT signature: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Invalid token: " + e.getMessage());
        }
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof UserDetailsImpl) {
            UserDetailsImpl userDetailsImpl = (UserDetailsImpl) userDetails;
            claims.put("Role", userDetailsImpl.getUserRole());
            claims.put("mobile", userDetails.getUsername());
        }
        return createToken(claims);
    }

    private String createToken(Map<String, Object> claims) {
        Key key = Keys.hmacShaKeyFor(base64Secret.getBytes());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails UserDetails) {
        final String username = extractUsername(token);
        return (username.equals(UserDetails.getUsername()) && !isTokenExpired(token));
    }
}

