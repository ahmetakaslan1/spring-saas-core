package com.ahmet.order_management.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Base Entity - Tüm entity'lerin ortak alanları
 * 
 * Tüm entity'ler bu sınıftan extend edilir.
 * 
 * Ortak alanlar:
 * - id: Primary key
 * - createdAt: Oluşturulma zamanı (otomatik)
 * - updatedAt: Güncellenme zamanı (otomatik)
 * - deletedAt: Silinme zamanı (soft delete için)
 * 
 * @MappedSuperclass: Bu sınıf tablo oluşturmaz, sadece alt sınıflara miras verir
 */
@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)  // Auditing için gerekli
public abstract class BaseEntity {
    
    /**
     * Primary Key (otomatik artan)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Oluşturulma zamanı (otomatik)
     * İlk kayıt oluşturulunca set edilir, sonra değişmez
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * Son güncellenme zamanı (otomatik)
     * Her update'te otomatik güncellenir
     */
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    /**
     * Silinme zamanı (soft delete)
     * null ise kayıt aktif
     * Değer varsa kayıt silinmiş (soft delete)
     * 
     * Soft Delete:
     * - Kayıt database'den silinmez
     * - Sadece deletedAt alanı set edilir
     * - Sorgularda WHERE deletedAt IS NULL ile filtrelenir
     */
    @Column
    private LocalDateTime deletedAt;
}
