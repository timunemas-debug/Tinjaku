package com.tinjaku.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getSignKey(){
        return Keys.hmacShaKeyFor(
            secretKey.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(UserDetails userDetails){

        String role;
        Long id;

        if (userDetails instanceof CustomUserDetails customUserDetails) {
            role = customUserDetails.getUser().getRole().name();
            id = customUserDetails.getUser().getUserId();
        }
        else if(userDetails instanceof CustomMitraDetails customMitraDetails){
            role = customMitraDetails.getMitra().getRole().name();
            id = customMitraDetails.getMitra().getMitraId();
        }else{
            throw new IllegalArgumentException("Unknown UserDetails type!");
        }

        return Jwts.builder()
                    .subject(userDetails.getUsername())
                    .claim("role", role)
                    .claim("id", id)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + expiration))
                    .signWith(getSignKey())
                    .compact();
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
    }

    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }

    public Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        String username = extractUsername(token);

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}