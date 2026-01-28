package com.ahmet.order_management.auth.controller;

import com.ahmet.order_management.auth.dto.LoginRequest;
import com.ahmet.order_management.auth.dto.LoginResponse;
import com.ahmet.order_management.auth.dto.RegisterRequest;
import com.ahmet.order_management.auth.service.AuthService;
import com.ahmet.order_management.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Giriş işlemleri")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Kullanıcı Girişi", description = "Kullanıcı adı ve şifre ile token alır.")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(response, "Giriş başarılı"));
    }

    @PostMapping("/register")
    @Operation(summary = "Yeni Kullanıcı Kaydı", description = "Yeni kullanıcı oluşturur ve otomatik giriş yapar.")
    public ResponseEntity<ApiResponse<LoginResponse>> register(@Valid @RequestBody RegisterRequest request) {
        LoginResponse response = authService.register(request);
        return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Kayıt başarılı"));
    }
}
