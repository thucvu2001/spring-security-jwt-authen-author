package com.example.security.service;

import com.example.security.entity.Role;

import java.util.List;

public interface RoleService {
    Role saveRole (Role role);

    Role findByName(String roleName);

    List<Role> getRolesWithUser(String username);
}
