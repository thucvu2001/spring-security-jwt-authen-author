package com.example.security.config;

import com.example.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationProvider authenticationProvider;
    private final UserService userService;

    // config CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Tắt CSRF
        httpSecurity.csrf().disable();

        // Kích hoạt CORS
        httpSecurity.cors();

        // Xác định chính sách quản lý phiên làm việc
        httpSecurity.sessionManagement().sessionCreationPolicy(STATELESS);

        httpSecurity.authorizeHttpRequests()
                // Cho phép yêu cầu OPTIONS từ tất cả các đường dẫn
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // config swagger
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()

                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/demo/login").hasAuthority("ROLE_USERS")
                .requestMatchers("/demo/get-roles").hasAuthority("ROLE_SUPER_ADMIN")
                .requestMatchers("/api/v1/client/**").permitAll()
                .and()

                // Yêu cầu các yêu cầu khác phải được xác thực
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()

                // Đặt AuthenticationProvider để xác thực người dùng
                .authenticationProvider(authenticationProvider)

                // Thêm JwtAuthenticationFilter vào trước UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
