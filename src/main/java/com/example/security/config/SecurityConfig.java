package com.example.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Tắt CSRF
        httpSecurity.csrf().disable();

        // Kích hoạt CORS
        httpSecurity.cors();

        // Xác định chính sách quản lý phiên làm việc
        httpSecurity.sessionManagement().sessionCreationPolicy(STATELESS);

        httpSecurity.authorizeRequests()
                // Cho phép yêu cầu OPTIONS từ tất cả các đường dẫn
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

//                config swagger
//                .requestMatchers("/v3/api-docs/**").permitAll()
//                .requestMatchers("/swagger-ui/**").permitAll()

                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/demo/login").hasAuthority("ROLE_USERS")
                .requestMatchers("demo/get-roles").hasAuthority("ROLE_USERS")
                .and()

                // Yêu cầu các yêu cầu khác phải được xác thực
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()

                // Đặt AuthenticationProvider để xác thực người dùng
                .authenticationProvider(authenticationProvider)

                // Thêm JwtAuthenticationFilter vào trước UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
