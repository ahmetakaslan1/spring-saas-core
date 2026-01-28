package com.ahmet.order_management.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Kullanıcı giriş isteği modeli")
public class LoginRequest {

    @NotBlank(message = "Kullanıcı adı boş olamaz")
    @Schema(description = "Kullanıcı adı", example = "ahmet")
    private String username;

    @NotBlank(message = "Şifre boş olamaz")
    @Schema(description = "Kullanıcı şifresi", example = "sifre123")
    private String password;
}
