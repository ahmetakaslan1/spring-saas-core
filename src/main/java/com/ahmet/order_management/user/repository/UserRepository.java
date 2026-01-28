package com.ahmet.order_management.user.repository;

import com.ahmet.order_management.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User Repository
 * 
 * Spring Data JPA otomatik olarak şu metodları sağlar:
 * - save(User) → Kaydet/Güncelle
 * - findById(Long) → ID ile bul
 * - findAll() → Hepsini getir
 * - deleteById(Long) → ID ile sil
 * - count() → Toplam sayı
 * 
 * Ama bunları yapamaz. Bunun için bizler custom metodlar yazıyoruz.
 * Daha kolay bir şekilde db ile iletişim kurabilmek için 
 * 
 * Custom metodlar:
 * - findByUsername(String) → Username ile bul (Login için)
 * - findByEmail(String) → Email ile bul
 * - existsByUsername(String) → Username var mı kontrol et
 * - existsByEmail(String) → Email var mı kontrol et
 */


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Username ile kullanıcı bul
     * gerisini kendisi yapacaktır.
     * 
     * Kullanım (Login):
     * Optional<User> user = userRepository.findByUsername("ahmet");
     * 
     * Spring Data JPA otomatik query oluşturur:
     * SELECT * FROM users WHERE username = ?
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Email ile kullanıcı bul
     * 
     * Kullanım:
     * Optional<User> user = userRepository.findByEmail("ahmet@example.com");
     * 
     * Spring Data JPA otomatik query oluşturur:
     * SELECT * FROM users WHERE email = ?
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Username var mı kontrol et
     * 
     * Kullanım (Validation):
     * if (userRepository.existsByUsername("ahmet")) {
     *     throw new BusinessException("Bu username zaten kullanılıyor");
     * }
     * 
     * Spring Data JPA otomatik query oluşturur:
     * SELECT COUNT(*) > 0 FROM users WHERE username = ?
     */
    boolean existsByUsername(String username);
    
    /**
     * Email var mı kontrol et
     * 
     * Kullanım (Validation):
     * if (userRepository.existsByEmail("ahmet@example.com")) {
     *     throw new BusinessException("Bu email zaten kullanılıyor");
     * }
     * 
     * Spring Data JPA otomatik query oluşturur:
     * SELECT COUNT(*) > 0 FROM users WHERE email = ?
     */
    boolean existsByEmail(String email);
}
