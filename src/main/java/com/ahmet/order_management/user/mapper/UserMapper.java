package com.ahmet.order_management.user.mapper;

import com.ahmet.order_management.user.dto.UserResponse;
import com.ahmet.order_management.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * User Mapper
 * 
 * Entity <-> DTO dönüşümlerini yapar.
 */

// Burada hazır kütüphaneler kullanılabilir.

/*
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

*/
@Component
public class UserMapper {
    
    /**
     * Entity -> Response DTO
     */
    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }
        
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .active(user.getActive())
                .organizationId(user.getOrganization().getId())
                .organizationName(user.getOrganization().getName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
    
    /**
     * List<Entity> -> List<Response DTO>
     */
    public List<UserResponse> toResponseList(List<User> users) {
        if (users == null) {
            return List.of();
        }
        
        return users.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
