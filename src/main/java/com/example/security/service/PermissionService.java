package com.example.security.service;

import java.util.List;

public interface PermissionService {
    String addApi(String permissionCode, List<String> apiCodes);
    String removeApi(String permissionCode, List<String> apiCodes);
}
