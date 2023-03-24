package com.example.security.service;

import com.example.security.auth.AuthenticationRequest;
import com.example.security.auth.AuthenticationResponse;
import com.example.security.entity.Role;
import com.example.security.entity.User;
import com.example.security.repository.RoleCustomRepository;
import com.example.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleCustomRepository roleCustomRepository;
    private final JwtService jwtService;

    public AuthenticationResponse authenticationResponse(AuthenticationRequest authenticationRequest) {
        // Authenticate
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword())
                );

        User user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();

        Set<Role> roleSet = new HashSet<>();

        if (user != null) {
            roleCustomRepository.getRolesWithUser(user)
                    .forEach(role -> roleSet.add(role));
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>(); // List quyền
        roleSet.stream().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        String jwtToken = jwtService.generateToken(user, authorities);
        String jwtRefreshToken = jwtService.generateRefreshToken(user, authorities);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(jwtRefreshToken)
                .build();
    }
}
