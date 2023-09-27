package com.example.security.service;

import com.example.security.entity.Api;
import com.example.security.entity.Permission;
import com.example.security.repository.ApiRepository;
import com.example.security.repository.PermissionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final ApiService apiService;

    @Override
    public String addApi(String permissionCode, List<String> apiCodes) {
        Permission permission = permissionRepository.findByPermissionCode(permissionCode);
        log.info(permission.toString());
        List<Api> apis = apiService.findAllByApiCode(apiCodes);
        log.info(apis.toString());
        apis.forEach(api -> permission.addApi(api));
        return "Add Api To Permission Success";
    }

    @Override
    public String removeApi(String permissionCode, List<String> apiCodes) {
        Permission permission = permissionRepository.findByPermissionCode(permissionCode);
        List<Api> apis = apiService.findAllByApiCode(apiCodes);
        apis.forEach(api -> permission.removeApi(api));
        return "Remove Api From Permission Success";
    }
}
