package com.example.security.service;

import com.example.security.entity.Role;

public interface RoleService {
    Role saveRole (Role role);

    Role findByName(String roleName);
}
