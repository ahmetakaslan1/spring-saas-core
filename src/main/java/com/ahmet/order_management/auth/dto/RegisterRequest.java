package com.ahmet.order_management.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Kullanıcı kayıt isteği")
public class RegisterRequest {

    @NotBlank(message = "Kullanıcı adı boş olamaz")
    @Size(min = 3, max = 50, message = "Kullanıcı adı 3-50 karakter arasında olmalıdır")
    @Schema(description = "Kullanıcı adı", example = "yeni_kullanici")
    private String username;

    @NotBlank(message = "Email boş olamaz")
    @Email(message = "Geçerli bir email adresi giriniz")
    @Schema(description = "E-posta", example = "yeni@example.com")
    private String email;

    @NotBlank(message = "Şifre boş olamaz")
    @Size(min = 6, message = "Şifre en az 6 karakter olmalıdır")
    @Schema(description = "Şifre (En az 6 karakter)", example = "gucluSifre123")
    private String password;

    @Schema(description = "Ad Soyad", example = "Yeni Kullanıcı")
    private String fullName;
    
    // Not: Public register işleminde genellikle OrganizationID ve Role backend'de default atanır veya token ile gelen bir davetiye kodu ile bağlanır.
    // Şimdilik basitlik adına opsiyonel bırakabiliriz veya ilk kayıt olanı admin yapacak bir logic kurabiliriz.
    // Bu örnekte basit kayıt alacağız, OrganizationID zorunlu olsun.
    
    @Schema(description = "Bağlı olacağı Organizasyon ID (Varsa)", example = "1")
    private Long organizationId;
}
