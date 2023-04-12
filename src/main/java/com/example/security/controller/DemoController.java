package com.example.security.controller;


import com.example.security.dto.AuthRequestDTO;
import com.example.security.entity.Role;
import com.example.security.service.AuthenticationService;
import com.example.security.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {
    private final AuthenticationService authenticationService;
    private final RoleService roleService;

    @GetMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Authentication and Authorization is succeed");
    }

    @GetMapping("/get-roles")
    public ResponseEntity<List<Role>> getRoles(@RequestBody AuthRequestDTO authRequestDTO) {
        List<Role> roleList = roleService.getRolesWithUser(authRequestDTO.getEmail());
        return ResponseEntity.ok(roleList);
    }
}
