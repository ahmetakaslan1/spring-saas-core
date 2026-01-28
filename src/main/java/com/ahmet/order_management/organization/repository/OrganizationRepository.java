package com.ahmet.order_management.organization.repository;

import com.ahmet.order_management.organization.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Organization Repository
 * 
 * Spring Data JPA otomatik olarak şu metodları sağlar:
 * - save(Organization) → Kaydet/Güncelle
 * - findById(Long) → ID ile bul
 * - findAll() → Hepsini getir
 * - deleteById(Long) → ID ile sil
 * - count() → Toplam sayı
 * 
 * Custom metodlar:
 * - findByName(String) → İsme göre bul
 * - existsByName(String) → İsim var mı kontrol et
 */
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    
    /**
     * Organizasyon adına göre bul
     * 
     * Kullanım:
     * Optional<Organization> org = organizationRepository.findByName("ABC Şirketi");
     * 
     * Spring Data JPA otomatik query oluşturur:
     * SELECT * FROM organizations WHERE name = ?
     */
    Optional<Organization> findByName(String name);
    
    /**
     * Organizasyon adı var mı kontrol et
     * 
     * Kullanım:
     * boolean exists = organizationRepository.existsByName("ABC Şirketi");
     * 
     * Spring Data JPA otomatik query oluşturur:
     * SELECT COUNT(*) > 0 FROM organizations WHERE name = ?
     */
    boolean existsByName(String name);
}
