package com.ahmet.order_management.common.exception;

import com.ahmet.order_management.common.dto.ApiResponse;
import com.ahmet.order_management.common.dto.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Global Exception Handler
 * 
 * Tüm controller'lardan fırlatılan exception'ları yakalar ve
 * standart bir response formatında döner.
 * 
 * 
 * 
 * 
 * - Controller'larda try-catch gereksiz
 * 
 * @RestControllerAdvice → Tüm controller'ları dinler
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * NotFoundException yakalanır
     * HTTP Status: 404 NOT FOUND
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException(NotFoundException ex) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .code("NOT_FOUND")
                .details(ex.getMessage())
                .build();
        
        ApiResponse<Void> response = ApiResponse.error(ex.getMessage(), errorDetails);
        
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }
    
    /**
     * BusinessException yakalanır
     * HTTP Status: 400 BAD REQUEST
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException ex) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .code("BUSINESS_ERROR")
                .details(ex.getMessage())
                .build();
        
        ApiResponse<Void> response = ApiResponse.error(ex.getMessage(), errorDetails);
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
    
    /**
     * UnauthorizedException yakalanır
     * HTTP Status: 403 FORBIDDEN
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnauthorizedException(UnauthorizedException ex) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .code("UNAUTHORIZED")
                .details(ex.getMessage())
                .build();
        
        ApiResponse<Void> response = ApiResponse.error(ex.getMessage(), errorDetails);
        
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(response);
    }
    
    /**
     * Validation Exception yakalanır
     * @Valid ile gelen hataları yakalar
     * 
     * Aynı field için birden fazla hata olabilir:
     * - username: ["Zorunlu", "En az 3 karakter"]
     * 
     * HTTP Status: 400 BAD REQUEST
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(
            MethodArgumentNotValidException ex) {
        
        // Field hatalarını topla (her field için birden fazla hata olabilir)
        Map<String, List<String>> fieldErrors = new HashMap<>();
        
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            
            // Field için liste yoksa oluştur, varsa ekle
            fieldErrors.computeIfAbsent(fieldName, k -> new ArrayList<>())
                       .add(errorMessage);
        });
        
        ErrorDetails errorDetails = ErrorDetails.builder()
                .code("VALIDATION_ERROR")
                .details("Girilen bilgiler geçersiz")
                .fieldErrors(fieldErrors)
                .build();
        
        ApiResponse<Void> response = ApiResponse.error("Doğrulama hatası", errorDetails);
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
    
    /**
     * Genel Exception yakalanır
     * Yukarıdaki hiçbirine uymayan hatalar
     * HTTP Status: 500 INTERNAL SERVER ERROR
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .code("INTERNAL_SERVER_ERROR")
                .details(ex.getMessage())
                .build();
        
        ApiResponse<Void> response = ApiResponse.error("Bir hata oluştu", errorDetails);
        
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
