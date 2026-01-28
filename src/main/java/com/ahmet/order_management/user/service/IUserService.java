package com.ahmet.order_management.user.service;

import com.ahmet.order_management.user.entity.Role;
import com.ahmet.order_management.user.entity.User;
import java.util.List;

public interface IUserService {
    
    List<User> getAllUsers();
    
    User getUserById(Long id);
    
    User getUserByUsername(String username);
    
    User createUser(String username, String email, String password, String fullName, Role role, Long organizationId);
    
    User updateUser(Long id, String email, String fullName);
    
    void changePassword(Long id, String newPassword);
    
    void deleteUser(Long id);
}
