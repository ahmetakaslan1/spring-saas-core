package com.ahmet.order_management.common.exception;

/**
 * İş kuralı ihlali durumunda fırlatılan exception.
 * 
 * Kullanım Senaryoları:
 * - Aynı username ile kullanıcı zaten var
 * - Kullanıcı kendini silmeye çalışıyor
 * - Geçersiz iş mantığı
 * 
 * HTTP Status: 400 BAD REQUEST
 * 
 * Örnek:
 * throw new BusinessException("Bu username zaten kullanılıyor");
 */
public class BusinessException extends RuntimeException {
    
    public BusinessException(String message) {
        super(message);
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
