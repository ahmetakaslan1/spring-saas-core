package com.ahmet.order_management.organization.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Organizasyon detayları yanıtı")
public class OrganizationResponse {
    
    @Schema(description = "Organizasyon ID")
    private Long id;
    
    @Schema(description = "Organizasyon adı")
    private String name;
    
    @Schema(description = "Organizasyon açıklaması")
    private String description;
    
    @Schema(description = "Aktiflik durumu")
    private Boolean active;
    
    @Schema(description = "Oluşturulma tarihi")
    private LocalDateTime createdAt;
    
    @Schema(description = "Güncellenme tarihi")
    private LocalDateTime updatedAt;
}
