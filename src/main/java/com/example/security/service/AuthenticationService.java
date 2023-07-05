package com.example.security.service;

import com.example.security.dto.AuthRequestDTO;
import com.example.security.dto.AuthResponseDTO;
import com.example.security.entity.User;
import com.example.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthResponseDTO authenticateLogin(AuthRequestDTO authRequestDTO) {
        // Authenticate
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequestDTO.getEmail(),
                authRequestDTO.getPassword())
        );

        User user = userRepository.findByEmail(authRequestDTO.getEmail()).orElseThrow();

        List<GrantedAuthority> authorities = new ArrayList<>(user.getAuthorities());

        String jwtToken = jwtService.generateToken(user, authorities);
        String jwtRefreshToken = jwtService.generateRefreshToken(user, authorities);
        return AuthResponseDTO.builder()
                .token(jwtToken)
                .refreshToken(jwtRefreshToken)
                .build();
    }
}
