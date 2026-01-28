package com.ahmet.order_management.common.exception;

/**
 * Kayıt bulunamadığında fırlatılan exception.
 * 
 * Kullanım Senaryoları:
 * - User ID ile arama yapıldı, bulunamadı
 * - Organization ID ile arama yapıldı, bulunamadı
 * 
 * HTTP Status: 404 NOT FOUND
 * 
 * Örnek:
 * throw new NotFoundException("User bulunamadı: " + userId);
 */
public class NotFoundException extends RuntimeException {
    
    public NotFoundException(String message) {
        super(message);
    }
    
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
