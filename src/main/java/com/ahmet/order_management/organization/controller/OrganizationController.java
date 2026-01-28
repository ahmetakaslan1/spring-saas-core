package com.ahmet.order_management.organization.controller;

import com.ahmet.order_management.common.dto.ApiResponse;
import com.ahmet.order_management.organization.dto.OrganizationRequest;
import com.ahmet.order_management.organization.dto.OrganizationResponse;
import com.ahmet.order_management.organization.entity.Organization;
import com.ahmet.order_management.organization.mapper.OrganizationMapper;
import com.ahmet.order_management.organization.service.IOrganizationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Organization Controller
 * 
 * REST API Endpoints:
 * - GET /api/organizations
 * - GET /api/organizations/{id}
 * - POST /api/organizations
 * - PUT /api/organizations/{id}
 * - DELETE /api/organizations/{id}
 */
@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
@Tag(name = "Organization Management", description = "Organizasyon yönetim işlemleri")
public class OrganizationController {
    
    private final IOrganizationService organizationService;
    private final OrganizationMapper organizationMapper;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrganizationResponse>>> getAllOrganizations() {
        List<Organization> organizations = organizationService.getAllOrganizations();
        List<OrganizationResponse> response = organizationMapper.toResponseList(organizations);
        
        return ResponseEntity.ok(ApiResponse.success(response, "Organizasyonlar listelendi"));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrganizationResponse>> getOrganizationById(@PathVariable Long id) {
        Organization organization = organizationService.getOrganizationById(id);
        OrganizationResponse response = organizationMapper.toResponse(organization);
        
        return ResponseEntity.ok(ApiResponse.success(response, "Organizasyon bulundu"));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<OrganizationResponse>> createOrganization(@Valid @RequestBody OrganizationRequest request) {
        Organization organization = organizationService.createOrganization(request.getName(), request.getDescription());
        OrganizationResponse response = organizationMapper.toResponse(organization);
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Organizasyon oluşturuldu"));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrganizationResponse>> updateOrganization(
            @PathVariable Long id,
            @Valid @RequestBody OrganizationRequest request) {
        
        Organization organization = organizationService.updateOrganization(id, request.getName(), request.getDescription());
        OrganizationResponse response = organizationMapper.toResponse(organization);
        
        return ResponseEntity.ok(ApiResponse.success(response, "Organizasyon güncellendi"));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrganization(@PathVariable Long id) {
        organizationService.deleteOrganization(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Organizasyon silindi"));
    }
}
