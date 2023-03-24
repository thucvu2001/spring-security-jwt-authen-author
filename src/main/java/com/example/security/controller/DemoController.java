package com.example.security.controller;


import com.example.security.auth.AuthenticationRequest;
import com.example.security.auth.AuthenticationResponse;
import com.example.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {
    private final AuthenticationService authenticationService;

    @GetMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Authentication and Authorization is succeed");
    }
}
