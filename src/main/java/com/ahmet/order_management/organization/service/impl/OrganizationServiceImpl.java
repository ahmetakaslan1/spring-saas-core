package com.ahmet.order_management.organization.service.impl;

import com.ahmet.order_management.common.exception.BusinessException;
import com.ahmet.order_management.common.exception.NotFoundException;
import com.ahmet.order_management.organization.entity.Organization;
import com.ahmet.order_management.organization.repository.OrganizationRepository;
import com.ahmet.order_management.organization.service.IOrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrganizationServiceImpl implements IOrganizationService {
    
    private final OrganizationRepository organizationRepository;
    
    @Override
    public List<Organization> getAllOrganizations() {
        log.debug("Tüm organizasyonlar getiriliyor");
        return organizationRepository.findAll();
    }
    
    @Override
    public Organization getOrganizationById(Long id) {
        log.debug("Organizasyon getiriliyor: id={}", id);
        return organizationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organizasyon bulunamadı: " + id));
    }
    
    @Override
    @Transactional
    public Organization createOrganization(String name, String description) {
        log.info("Yeni organizasyon oluşturuluyor: name={}", name);
        
        if (organizationRepository.existsByName(name)) {
            throw new BusinessException("Bu isimde organizasyon zaten var: " + name);
        }
        
        Organization organization = Organization.builder()
                .name(name)
                .description(description)
                .active(true)
                .build();
        
        Organization saved = organizationRepository.save(organization);
        log.info("Organizasyon oluşturuldu: id={}, name={}", saved.getId(), saved.getName());
        
        return saved;
    }
    
    @Override
    @Transactional
    public Organization updateOrganization(Long id, String name, String description) {
        log.info("Organizasyon güncelleniyor: id={}", id);
        
        Organization organization = getOrganizationById(id);
        
        if (!organization.getName().equals(name) && organizationRepository.existsByName(name)) {
            throw new BusinessException("Bu isimde organizasyon zaten var: " + name);
        }
        
        organization.setName(name);
        organization.setDescription(description);
        
        Organization updated = organizationRepository.save(organization);
        log.info("Organizasyon güncellendi: id={}, name={}", updated.getId(), updated.getName());
        
        return updated;
    }
    
    @Override
    @Transactional
    public void deleteOrganization(Long id) {
        log.info("Organizasyon siliniyor: id={}", id);
        
        Organization organization = getOrganizationById(id);
        
        organization.setDeletedAt(java.time.LocalDateTime.now());
        organizationRepository.save(organization);
        
        log.info("Organizasyon silindi (soft delete): id={}", id);
    }
}
