package com.ahmet.order_management.common.exception;

/**
 * Yetkilendirme hatası durumunda fırlatılan exception.
 * 
 * Kullanım Senaryoları:
 * - JWT token geçersiz
 * - Token süresi dolmuş
 * - Kullanıcı yetkisi yok (ADMIN değil)
 * 
 * HTTP Status: 403 FORBIDDEN
 * 
 * Örnek:
 * throw new UnauthorizedException("Bu işlem için yetkiniz yok");
 */
public class UnauthorizedException extends RuntimeException {
    
    public UnauthorizedException(String message) {
        super(message);
    }
    
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
