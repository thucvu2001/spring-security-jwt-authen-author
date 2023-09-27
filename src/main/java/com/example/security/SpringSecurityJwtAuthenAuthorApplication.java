package com.example.security;

import com.example.security.entity.Role;
import com.example.security.entity.User;
import com.example.security.service.RoleService;
import com.example.security.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.HashSet;

@SpringBootApplication
@EnableWebSecurity
@EnableJpaRepositories
public class SpringSecurityJwtAuthenAuthorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJwtAuthenAuthorApplication.class, args);
    }

//    Chay lần đầu sau đó comment lại

//    @Bean
//    CommandLineRunner commandLineRunner(UserService userService, RoleService roleService) {
//        return args -> {
//            roleService.saveRole(new Role(null, "ROLE_USERS", new HashSet<>(), new HashSet<>()));
//            roleService.saveRole(new Role(null, "ROLE_MANAGER", new HashSet<>(), new HashSet<>()));
//            roleService.saveRole(new Role(null, "ROLE_ADMIN", new HashSet<>(), new HashSet<>()));
//            roleService.saveRole(new Role(null, "ROLE_SUPER_ADMIN", new HashSet<>(), new HashSet<>()));
//
//            userService.saveUser(new User(null, "Thuc Vu 01", "thucvu01", "vuthuc3152001@gmail.com", "123456", new HashSet<>()));
//            userService.saveUser(new User(null, "Thuc Vu 02", "thucvu02", "vuthuc3152002@gmail.com", "123456", new HashSet<>()));
//            userService.saveUser(new User(null, "Thuc Vu 03", "thucvu03", "vuthuc3152003@gmail.com", "123456", new HashSet<>()));
//            userService.saveUser(new User(null, "Thuc Vu 04", "thucvu04", "vuthuc3152004@gmail.com", "123456", new HashSet<>()));
//
//            userService.addRoleToUser("vuthuc3152001@gmail.com", "ROLE_USERS");
//            userService.addRoleToUser("vuthuc3152002@gmail.com", "ROLE_MANAGER");
//            userService.addRoleToUser("vuthuc3152003@gmail.com", "ROLE_ADMIN");
//            userService.addRoleToUser("vuthuc3152004@gmail.com", "ROLE_SUPER_ADMIN");
//        };
//    }

}
