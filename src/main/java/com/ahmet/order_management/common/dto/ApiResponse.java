package com.ahmet.order_management.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Tüm API endpoint'lerinden dönen standart response yapısı.
 * 
 * Generic <T> sayesinde her türlü data dönebiliriz:
 * - Tekil obje: ApiResponse<UserResponse>
 * - Liste: ApiResponse<List<UserResponse>>
 * - Void: ApiResponse<Void>
 * 
 * @param <T> Response data tipi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // Hata alabiliriz bu yüzden Null alanları JSON'a dahil etme
public class ApiResponse<T> {
    
    /**
     * İşlem başarılı mı?
     * - true: İşlem başarılı, data dolu
     * - false: Hata var, error dolu
     */
    private boolean success;
    
    /**
     * Kullanıcıya gösterilecek mesaj
     * Örnekler:
     * - "Kullanıcı başarıyla oluşturuldu"
     * - "Giriş başarılı"
     * - "Yetkiniz yok"
     */
    private String message;
    
    /**
     * Başarılı işlemlerde dönen data
     * Hata durumunda null olabilir
     */
    private T data;
    
    /**
     * Hata durumunda dönen detaylar
     * Başarılı işlemlerde null
     */
    private ErrorDetails error;
    
    /**
     * Response oluşturulma zamanı
     * Frontend'de log tutmak için kullanılabilir bu yüzden eklemek faydalıdır.
     */
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    
    // ============================================
    // STATIC FACTORY METHODS (Okunabilirlik için)
    // ============================================
    
    /**
     * Başarılı response (data + mesaj)
     * 
     * Kullanım:
     * return ApiResponse.success(user, "Kullanıcı oluşturuldu");
     * yazım şekli bu bir tık değişik.
     * Burad <T> generic tir ve sen ApiResponse<T> class'ını kullanacaksın adında success
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }
    
    /**
     * Başarılı response (sadece data, default mesaj)
     * 
     * Kullanım:
     * return ApiResponse.success(user);
     */
    public static <T> ApiResponse<T> success(T data) {
        return success(data, "İşlem başarılı");
    }
    
    /**
     * Başarılı response (sadece mesaj, data yok)
     * Örnek: Silme işlemi
     * 
     * Kullanım:
     * return ApiResponse.success("Kullanıcı silindi");
     */
    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
    
    /**
     * Hata response (mesaj + error detayları)
     * 
     * Kullanım:
     * return ApiResponse.error("Kullanıcı bulunamadı", errorDetails);
     */
    public static <T> ApiResponse<T> error(String message, ErrorDetails error) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .error(error)
                .timestamp(LocalDateTime.now())
                .build();
    }
    
    /**
     * Hata response (sadece mesaj)
     * 
     * Kullanım:
     * return ApiResponse.error("Bir hata oluştu");
     */
    public static <T> ApiResponse<T> error(String message) {
        return error(message, null);
    }
}
