package com.example.security.service;

import java.util.List;

public interface PermissionService {
    void addApiToPermission (String permissionCode, List<String> apiCode);
}
