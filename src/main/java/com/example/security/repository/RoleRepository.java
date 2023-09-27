package com.example.security.repository;

import com.example.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleCode(String roleCode);

    @Query(nativeQuery = true, value = """
            select r.id as id, r.name as name
            from users u
            join users_roles ur on u.id = ur.users_id
            join roles r on r.id = ur.roles_id
            where u.email = ?1
            """)
    List<Role> getRolesWithUser(String username);
}
