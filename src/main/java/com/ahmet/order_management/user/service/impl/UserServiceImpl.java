package com.ahmet.order_management.user.service.impl;

import com.ahmet.order_management.common.exception.BusinessException;
import com.ahmet.order_management.common.exception.NotFoundException;
import com.ahmet.order_management.organization.entity.Organization;
import com.ahmet.order_management.organization.repository.OrganizationRepository;
import com.ahmet.order_management.user.entity.Role;
import com.ahmet.order_management.user.entity.User;
import com.ahmet.order_management.user.repository.UserRepository;
import com.ahmet.order_management.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements IUserService {
    
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public List<User> getAllUsers() {
        log.debug("Tüm kullanıcılar getiriliyor");
        return userRepository.findAll();
    }
    
    @Override
    public User getUserById(Long id) {
        log.debug("Kullanıcı getiriliyor: id={}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Kullanıcı bulunamadı: " + id));
    }
    
    @Override
    public User getUserByUsername(String username) {
        log.debug("Kullanıcı getiriliyor: username={}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Kullanıcı bulunamadı: " + username));
    }
    
    @Override
    @Transactional
    public User createUser(String username, String email, String password, 
                          String fullName, Role role, Long organizationId) {
        log.info("Yeni kullanıcı oluşturuluyor: username={}", username);
        
        if (userRepository.existsByUsername(username)) {
            throw new BusinessException("Bu username zaten kullanılıyor: " + username);
        }
        
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException("Bu email zaten kullanılıyor: " + email);
        }
        
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new NotFoundException("Organizasyon bulunamadı: " + organizationId));
        
        String encodedPassword = passwordEncoder.encode(password);
        
        User user = User.builder()
                .username(username)
                .email(email)
                .password(encodedPassword)
                .fullName(fullName)
                .role(role)
                .active(true)
                .organization(organization)
                .build();
        
        User saved = userRepository.save(user);
        log.info("Kullanıcı oluşturuldu: id={}, username={}", saved.getId(), saved.getUsername());
        
        return saved;
    }
    
    @Override
    @Transactional
    public User updateUser(Long id, String email, String fullName) {
        log.info("Kullanıcı güncelleniyor: id={}", id);
        
        User user = getUserById(id);
        
        if (!user.getEmail().equals(email) && userRepository.existsByEmail(email)) {
            throw new BusinessException("Bu email zaten kullanılıyor: " + email);
        }
        
        user.setEmail(email);
        user.setFullName(fullName);
        
        User updated = userRepository.save(user);
        log.info("Kullanıcı güncellendi: id={}, username={}", updated.getId(), updated.getUsername());
        
        return updated;
    }
    
    @Override
    @Transactional
    public void changePassword(Long id, String newPassword) {
        log.info("Kullanıcı şifresi değiştiriliyor: id={}", id);
        
        User user = getUserById(id);
        
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        
        userRepository.save(user);
        log.info("Kullanıcı şifresi değiştirildi: id={}", id);
    }
    
    @Override
    @Transactional
    public void deleteUser(Long id) {
        log.info("Kullanıcı siliniyor: id={}", id);
        
        User user = getUserById(id);
        
        user.setDeletedAt(java.time.LocalDateTime.now());
        userRepository.save(user);
        
        log.info("Kullanıcı silindi (soft delete): id={}", id);
    }
}
