package com.ahmet.order_management.organization.entity;

import com.ahmet.order_management.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * Organization (Organizasyon) entity
 * 
 * Her kullanıcı bir organizasyona ait olmalıdır.
 * Örnek: "ABC Şirketi", "XYZ Holding"
 * 
 * Tablo adı: organizations
 * 
 * BaseEntity'den miras alır:
 * - id, createdAt, updatedAt, deletedAt
 */
@Entity
@Table(name = "organizations")
@Data
@EqualsAndHashCode(callSuper = true)  // BaseEntity'deki alanları da equals/hashCode'a dahil et
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organization extends BaseEntity {
    
    /**
     * Organizasyon adı
     * Örnek: "ABC Şirketi"
     */
    @Column(nullable = false, unique = true, length = 100)
    private String name;
    
    /**
     * Organizasyon açıklaması (opsiyonel)
     */
    @Column(length = 500)
    private String description;
    
    /**
     * Aktif mi?
     * false ise organizasyon devre dışı (kullanıcılar giriş yapamaz)
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;
}
