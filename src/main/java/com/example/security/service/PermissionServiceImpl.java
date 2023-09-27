package com.example.security.service;

import com.example.security.entity.Api;
import com.example.security.entity.Permission;
import com.example.security.repository.ApiRepository;
import com.example.security.repository.PermissionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final ApiRepository apiRepository;

    @Override
    public void addApiToPermission(String permissionCode, List<String> apiCode) {
        Permission permission = permissionRepository.findByPermissionCode(permissionCode);
        List<Api> apis = apiRepository.findAllByApiCodes(apiCode);
        permission.getListApi().addAll(apis);
        permissionRepository.save(permission);
    }
}
