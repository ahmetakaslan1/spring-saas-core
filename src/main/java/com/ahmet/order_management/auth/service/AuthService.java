package com.ahmet.order_management.auth.service;

import com.ahmet.order_management.auth.dto.LoginRequest;
import com.ahmet.order_management.auth.dto.LoginResponse;
import com.ahmet.order_management.auth.dto.RegisterRequest;
import com.ahmet.order_management.common.exception.BusinessException;
import com.ahmet.order_management.common.util.JwtUtil;
import com.ahmet.order_management.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final com.ahmet.order_management.user.service.IUserService userService;

    public LoginResponse login(LoginRequest request) {
        log.info("Login denemesi: {}", request.getUsername());

        try {
            // 1. Kullanıcı adı ve şifre doğrulaması (Spring Security yapar)
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // 2. Başarılı ise token üret
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);

            // 3. User rolünü al (entity'den veya userDetails'den)
            // UserDetails'den role almak daha güvenli (GrantedAuthority'den)
            String role = userDetails.getAuthorities().stream()
                    .findFirst()
                    .map(item -> item.getAuthority())
                    .orElse("ROLE_USER");

            log.info("Login başarılı: {}", request.getUsername());

            return LoginResponse.builder()
                    .token(token)
                    .username(request.getUsername())
                    .role(role)
                    .build();

        } catch (Exception e) {
            log.warn("Login başarısız: {} - Hata: {}", request.getUsername(), e.getMessage());
            throw new BusinessException("Kullanıcı adı veya şifre hatalı");
        }
    }

    public String register(RegisterRequest request) {
        log.info("Yeni kullanıcı kaydı: {}", request.getUsername());
        
        // 1. Kullanıcıyı oluştur
        // Organization ID null ise 1 varsayıyoruz (Demo amaçlı)
        Long orgId = request.getOrganizationId() != null ? request.getOrganizationId() : 1L;
        
        userService.createUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getFullName(),
                com.ahmet.order_management.user.entity.Role.USER,
                orgId
        );
        
        log.info("Kullanıcı başarıyla oluşturuldu: {}", request.getUsername());
        
        return "Kullanıcı başarıyla oluşturuldu";
    }
}
