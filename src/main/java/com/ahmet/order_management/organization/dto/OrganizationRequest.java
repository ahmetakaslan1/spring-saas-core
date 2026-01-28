package com.ahmet.order_management.organization.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Organizasyon oluşturma/güncelleme isteği")
public class OrganizationRequest {
    
    @NotBlank(message = "Organizasyon adı boş olamaz")
    @Size(min = 2, max = 100, message = "Organizasyon adı 2-100 karakter arasında olmalıdır")
    @Schema(description = "Organizasyon adı", example = "Teknoloji A.Ş.")
    private String name;
    
    @Size(max = 500, message = "Açıklama en fazla 500 karakter olabilir")
    @Schema(description = "Organizasyon açıklaması", example = "Yazılım ve teknoloji çözümleri")
    private String description;
}
