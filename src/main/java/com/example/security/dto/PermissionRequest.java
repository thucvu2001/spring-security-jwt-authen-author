package com.example.security.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PermissionRequest {
    private String permissionCode;
    private List<String> apiCodes;
}
