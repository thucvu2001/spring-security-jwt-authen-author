package com.example.security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
// OncePerRequestFilter là một lớp trừu tượng trong Spring Security được sử dụng để tạo bộ lọc (filter) xử lý yêu cầu HTTP một lần duy nhất cho mỗi yêu cầu.

    // Khai báo khoá bí mật
    private static final String SECRET_KEY = "c342dd5ff691b05ce02f7d0dd292a65eb4d89965";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Lấy giá trị Authorization header từ yêu cầu HTTP
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                // Tách lấy chuỗi token từ giá trị Authorization header
                String token = authorizationHeader.substring("Bearer ".length());

                // Khởi tạo đối tượng Algorithm với thuật toán HMAC256 và khóa bí mật
                Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());

                // Xác minh tính hợp lệ của token bằng cách sử dụng khóa bí mật
                JWTVerifier jwtVerifier = JWT.require(algorithm).build(); // tạo đối tượng xác thực
                DecodedJWT decodedJWT = jwtVerifier.verify(token); // Xác thực và trả về thông tin giải mã từ token

                // Lấy tên người dùng từ token
                String username = decodedJWT.getSubject();

                // Lấy danh sách quyền (roles) từ token
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

                // Tạo một danh sách các GrantedAuthority từ danh sách quyền
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                Arrays.stream(roles).forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(role));
                });

                // Tạo một đối tượng UsernamePasswordAuthenticationToken để đại diện cho người dùng đã xác thực
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        username,
                        null, // không sử dụng mật khẩu
                        authorities
                );

                // Đặt người dùng đang được xác thực vào SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                // Chuyển tiếp yêu cầu tới các bộ lọc (filters) tiếp theo trong chuỗi
                filterChain.doFilter(request, response);

            } catch (Exception exception) {
                // Xử lý ngoại lệ nếu có lỗi xác thực
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                response.setContentType(APPLICATION_JSON_VALUE);
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            // Nếu không có header Authorization hoặc không bắt đầu bằng "Bearer ", chuyển tiếp yêu cầu tới các bộ lọc (filters) tiếp theo trong chuỗi
            filterChain.doFilter(request, response);
        }
    }
}
