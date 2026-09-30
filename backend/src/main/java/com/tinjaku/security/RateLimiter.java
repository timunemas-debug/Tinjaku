package com.tinjaku.security;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimiter extends OncePerRequestFilter{
    
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public Bucket createBucket(){
        Bandwidth limit = Bandwidth.builder()
            .capacity(5)
            .refillGreedy(5, Duration.ofMinutes(1))
            .build();

        return Bucket.builder()
            .addLimit(limit)
            .build();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException{

        if (!request.getRequestURI().equals("/auth/login-user")
                && !request.getRequestURI().equals("auth/login-mitra")
                && !request.getRequestURI().equals("auth/login-admin")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!request.getMethod().equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        String ip = request.getRemoteAddr();

        Bucket bucket = buckets.computeIfAbsent(ip, key -> createBucket());

        if (!bucket.tryConsume(1)) {
            response.setStatus(429);
            return;
        }

        filterChain.doFilter(request, response);
    }
}