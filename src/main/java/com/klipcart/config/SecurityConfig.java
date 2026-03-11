package com.klipcart.config;

import com.klipcart.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 🌟 புதிதாக இணைக்கப்பட்ட JWT Filter 🌟
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) 
            .cors(cors -> cors.configure(http))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/register", "/login", "/admin-login", "/api/products/**").permitAll() 
                .requestMatchers("/api/admin/**").hasRole("ADMIN") // Admin API
                .anyRequest().permitAll() 
            )
            // 🌟 Spring Security-க்கு முன்பாக நமது JWT Filter-ஐ வேலை செய்ய சொல்கிறோம் 🌟
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); 
            
        return http.build();
    }
}