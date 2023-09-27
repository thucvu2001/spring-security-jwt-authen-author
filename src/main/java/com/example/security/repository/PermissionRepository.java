package com.example.security.repository;

import com.example.security.entity.Api;
import com.example.security.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Permission findByPermissionCode(String permissionCode);
}
