package com.ahmet.order_management.auth.service;

import com.ahmet.order_management.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom User Details Service
 * 
 * Spring Security'nin user bulma mekanizmasını override ediyoruz.
 * Standart "in-memory user" yerine bizim veritabanımızdan bakacak.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Repository'den user'ı bul, yoksa hata fırlat
        // User entity'miz UserDetails implement ettiği için direkt dönebiliriz
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + username));
    }
}
