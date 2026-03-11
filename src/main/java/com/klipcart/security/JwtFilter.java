package com.klipcart.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 1. Frontend-ல் இருந்து வரும் Authorization Header-ஐ எடுக்கிறோம்
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // "Bearer " என்பதை நீக்கிவிட்டு Token-ஐ மட்டும் எடுக்கிறோம்

            try {
                // 2. Token-ஐ சரிபார்க்கிறோம்
                Claims claims = jwtUtil.extractClaims(token);
                String email = claims.getSubject();
                String role = claims.get("role", String.class);

                // 3. Token சரியாக இருந்தால் Spring Security-க்கு அனுமதி கொடுக்கிறோம்
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                    UsernamePasswordAuthenticationToken authToken = 
                            new UsernamePasswordAuthenticationToken(email, null, Collections.singletonList(authority));
                    
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                System.out.println("Invalid or Expired Token!");
            }
        }
        
        chain.doFilter(request, response);
    }
}