package com.ahmet.order_management.organization.service;

import com.ahmet.order_management.organization.entity.Organization;
import java.util.List;

public interface IOrganizationService {
    
    List<Organization> getAllOrganizations();
    
    Organization getOrganizationById(Long id);
    
    Organization createOrganization(String name, String description);
    
    Organization updateOrganization(Long id, String name, String description);
    
    void deleteOrganization(Long id);
}
