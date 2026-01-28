package com.ahmet.order_management.user.controller;

import com.ahmet.order_management.common.dto.ApiResponse;
import com.ahmet.order_management.user.dto.UserRequest;
import com.ahmet.order_management.user.dto.UserResponse;
import com.ahmet.order_management.user.entity.User;
import com.ahmet.order_management.user.mapper.UserMapper;
import com.ahmet.order_management.user.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User Controller
 * 
 * REST API Endpoints:
 * - GET /api/users
 * - GET /api/users/{id}
 * - POST /api/users
 * - PUT /api/users/{id}
 * - DELETE /api/users/{id}
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "Kullanıcı yönetim işlemleri")
public class UserController {
    
    private final IUserService userService;
    private final UserMapper userMapper;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponse> response = userMapper.toResponseList(users);
        
        return ResponseEntity.ok(ApiResponse.success(response, "Kullanıcılar listelendi"));
    }
    
    @GetMapping("/me")
    @Operation(summary = "Kendi Profilim", description = "Giriş yapmış kullanıcının kendi bilgilerini getirir.")
    public ResponseEntity<ApiResponse<UserResponse>> getMe(java.security.Principal principal) {
        // Principal, SecurityContext'ten gelen logged-in user bilgisidir.
        String username = principal.getName();
        
        User user = userService.getUserByUsername(username);
        UserResponse response = userMapper.toResponse(user);
        
        return ResponseEntity.ok(ApiResponse.success(response, "Profil bilgileri getirildi"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Kullanıcı Getir (ID)", description = "ID ile kullanıcı detayı getirir. (Genellikle Admin kullanımı içindir)")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserResponse response = userMapper.toResponse(user);
        
        return ResponseEntity.ok(ApiResponse.success(response, "Kullanıcı bulundu"));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody UserRequest request) {
        User user = userService.createUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getFullName(),
                request.getRole(),
                request.getOrganizationId()
        );
        UserResponse response = userMapper.toResponse(user);
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Kullanıcı oluşturuldu"));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request) {
        
        // Not: UserRequest login/create için tasarlandı (password vs var)
        // Update için ayrı bir DTO daha iyi olur ama şimdilik alanları manuel seçiyoruz
        // Pratikte UserUpdateRequest DTO'su oluşturmak daha doğrudur
        User user = userService.updateUser(id, request.getEmail(), request.getFullName());
        UserResponse response = userMapper.toResponse(user);
        
        return ResponseEntity.ok(ApiResponse.success(response, "Kullanıcı güncellendi"));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Kullanıcı silindi"));
    }
}
