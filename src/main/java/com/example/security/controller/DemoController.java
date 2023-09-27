package com.example.security.controller;


import com.example.security.dto.AuthRequestDTO;
import com.example.security.dto.PermissionRequest;
import com.example.security.entity.Permission;
import com.example.security.entity.Role;
import com.example.security.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {
    private final UserService userService;
    private final RoleService roleService;
    private final ApiService apiService;
    private final IpService ipService;
    private final PermissionService permissionService;

    @GetMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Authentication and Authorization is succeed");
    }

    @PostMapping("/get-roles")
    public ResponseEntity<List<Role>> getRoles(@RequestBody AuthRequestDTO authRequestDTO) {
        List<Role> roleList = roleService.getRolesWithUser(authRequestDTO.getEmail());
        return ResponseEntity.ok(roleList);
    }

    @PostMapping("/add-role")
    public ResponseEntity<Role> addRole(@RequestBody Role role, @RequestBody List<Permission> permissions) {
        roleService.saveRole(role);
        return null;
    }

    @PostMapping("/add-api-to-permission")
    public String addApiToPermission (@RequestBody PermissionRequest request) {
        permissionService.addApi(request.getPermissionCode(), request.getApiCodes());
        return "Success";
    }

    @PostMapping("/remove-api-from-permission")
    public String removeApiFromPermission (@RequestBody PermissionRequest request) {
        permissionService.removeApi(request.getPermissionCode(), request.getApiCodes());
        return "Success";
    }
}
