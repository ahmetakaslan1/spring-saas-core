package com.ahmet.order_management.config;

import com.ahmet.order_management.common.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.Nonnull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT Authentication Filter
 * 
 * Her HTTP isteğinde araya girer (Filter).
 * Header'da "Authorization: Bearer <token>" var mı diye bakar.
 * Varsa token'ı doğrular ve kullanıcıyı sisteme giriş yapmış sayar.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService; // Spring Security'nin user servisi

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 1. Header kontrolü: "Bearer " ile başlamıyorsa geç
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Token'ı al (Bearer kelimesini at)
        jwt = authHeader.substring(7);
        
        try {
            // 3. Username'i token'dan çıkar
            username = jwtUtil.extractUsername(jwt);

            // 4. Token valid ise ve kullanıcı henüz authenticate olmamışsa
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                
                // User bilgilerini DB'den çek
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // 5. Token doğrulama
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    
                    // Authentication objesi oluştur
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    // SecurityContext'e (Sisteme) kaydet
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.debug("User authenticated via JWT: {}", username);
                }
            }
        } catch (Exception e) {
            log.error("JWT Authentication error: {}", e.getMessage());
            // Token hatası olsa bile zinciri devam ettir, SecurityConfig 403 verecek zaten
        }

        // Sonraki filtreye geç
        filterChain.doFilter(request, response);
    }
}
