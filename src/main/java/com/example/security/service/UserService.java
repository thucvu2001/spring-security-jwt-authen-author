package com.example.security.service;

import com.example.security.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    User saveUser(User user);
    void addRoleToUser (String username, String roleName);
    Optional<User> findByUsername(String username);
}
