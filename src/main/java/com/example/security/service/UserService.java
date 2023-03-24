package com.example.security.service;

import com.example.security.entity.User;

public interface UserService {
    User saveUser(User user);
    void addRoleToUser (String username, String roleName);
}
