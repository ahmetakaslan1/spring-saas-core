package com.ahmet.order_management.organization.mapper;

import com.ahmet.order_management.organization.dto.OrganizationResponse;
import com.ahmet.order_management.organization.entity.Organization;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Organization Mapper
 * 
 * Entity <-> DTO dönüşümlerini yapar.
 * Manuel mapping kullanıyoruz (ModelMapper veya MapStruct yerine).
 * Neden? Daha kontrollü ve performanslı.
 */
@Component
public class OrganizationMapper {
    
    /**
     * Entity -> Response DTO
     */
    public OrganizationResponse toResponse(Organization organization) {
        if (organization == null) {
            return null;
        }
        
        return OrganizationResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .description(organization.getDescription())
                .active(organization.getActive())
                .createdAt(organization.getCreatedAt())
                .updatedAt(organization.getUpdatedAt())
                .build();
    }
    
    /**
     * List<Entity> -> List<Response DTO>
     */
    public List<OrganizationResponse> toResponseList(List<Organization> organizations) {
        if (organizations == null) {
            return List.of();
        }
        
        return organizations.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
