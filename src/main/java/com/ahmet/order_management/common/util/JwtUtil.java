package com.ahmet.order_management.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT Yardımcı Sınıfı (Utility Class)
 * 
 * Görevleri:
 * 1. Token oluşturmak (generateToken)
 * 2. Token doğrulamak (validateToken)
 * 3. Token'dan bilgi okumak (extractUsername, extractExpiration)
 */
@Component
public class JwtUtil {

    // application.yaml'dan değerleri okuyoruz
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    /**
     * Secret key'i güvenli bir Key objesine çevirir (HMAC-SHA algoritması için)
     */
    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Token'dan kullanıcı adını çıkarır
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Token'dan son kullanma tarihini çıkarır
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Token içinden özel bir bilgiyi (Claim) okur
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Token'ı parse eder ve içindeki tüm bilgileri (Claims) döner
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey()) // İmzayı doğrula
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Token süresi dolmuş mu kontrol eder
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Kullanıcı için yeni bir token oluşturur
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Kullanıcı adı ile doğrudan token oluşturur (UserDetails gerekmeden)
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username) // Kullanıcı adı
                .issuedAt(new Date(System.currentTimeMillis())) // Oluşturulma zamanı
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration)) // Bitiş zamanı
                .signWith(getSignInKey()) // İmzala
                .compact();
    }

    /**
     * Token oluşturma (imzalama) işlemi
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims) // Ekstra bilgiler (varsa)
                .subject(userDetails.getUsername()) // Kullanıcı adı
                .issuedAt(new Date(System.currentTimeMillis())) // Oluşturulma zamanı
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration)) // Bitiş zamanı
                .signWith(getSignInKey()) // İmzala
                .compact();
    }

    /**
     * Token geçerli mi kontrol eder
     * 1. Kullanıcı adı uyuşuyor mu?
     * 2. Süresi dolmamış mı?
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
