package com.ahmet.order_management.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Hata durumlarında dönen detaylı bilgiler.
 * 
 * Kullanım Senaryoları:
 * 1. Validation hataları → fieldErrors dolu
 * 2. Business exception → sadece code ve message
 * 3. System exception → stackTrace (sadece dev ortamında)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {
    
    /**
     * Hata kodu (Frontend'de switch-case için)
     * Örnekler:
     * - "USER_NOT_FOUND"
     * - "UNAUTHORIZED"
     * - "VALIDATION_ERROR"
     */
    private String code;
    
    /**
     * Teknik hata mesajı (log için)
     */
    private String details;
    
    /**
     * Validation hataları
     * Map<field, List<error_messages>>
     * 
     * Örnek:
     * {
     *   "username": ["Kullanıcı adı zorunlu", "En az 3 karakter"],
     *   "email": ["Geçersiz email formatı"]
     * }
     */
    private Map<String, List<String>> fieldErrors;
    
    /**
     * Stack trace (sadece development ortamında)
     * Production'da null olmalı
     */
    private String stackTrace;
}
