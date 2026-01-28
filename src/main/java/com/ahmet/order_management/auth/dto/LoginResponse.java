package com.ahmet.order_management.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Kullanıcı giriş yanıtı")
public class LoginResponse {

    @Schema(description = "JWT Token")
    private String token;
    
    @Builder.Default
    @Schema(description = "Token tipi", example = "Bearer")
    private String type = "Bearer";
    
    @Schema(description = "Giriş yapan kullanıcı adı")
    private String username;
    
    @Schema(description = "Kullanıcı rolü", example = "ROLE_USER")
    private String role;
}
