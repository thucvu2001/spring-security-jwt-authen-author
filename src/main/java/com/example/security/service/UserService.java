package com.example.security.service;

import com.example.security.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User saveUser(User user);
    void addRoleToUser (String username, String roleName);
}
