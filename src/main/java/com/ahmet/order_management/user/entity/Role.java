package com.ahmet.order_management.user.entity;

/**
 * Kullanıcı rolleri
 * 
 * ADMIN: Tüm yetkilere sahip
 * - Kullanıcı oluşturabilir
 * - Kullanıcı silebilir
 * - Tüm kullanıcıları görebilir
 * 
 * USER: Sınırlı yetkiler
 * - Sadece kendi bilgilerini görebilir
 * - Kendi bilgilerini güncelleyebilir
 */
public enum Role {
    ADMIN,
    USER
}
