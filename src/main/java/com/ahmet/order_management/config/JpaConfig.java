package com.ahmet.order_management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA Configuration
 * 
 * @EnableJpaAuditing:
 * - Entity'lerdeki @CreatedDate ve @LastModifiedDate alanlarını otomatik doldurur
 * - createdAt ve updatedAt alanları için gerekli
 * 
 * Bu sayede:
 * - Yeni kayıt oluşturulunca createdAt otomatik set edilir
 * - Kayıt güncellenince updatedAt otomatik güncellenir
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {
    // Bu sınıf boş olabilir, sadece @EnableJpaAuditing aktif etmek için
}
