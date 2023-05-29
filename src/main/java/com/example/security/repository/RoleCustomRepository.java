package com.example.security.repository;

import com.example.security.entity.Role;
import com.example.security.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoleCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Role> getRolesWithUser(User user) {
        StringBuilder sql = new StringBuilder();
        sql.append("select r.name as name ");
        sql.append("from users u ");
        sql.append("join users_roles ur on u.id = ur.users_id ");
        sql.append("join roles r on r.id = ur.roles_id ");
        sql.append("where u.email = :email");

        NativeQuery<Role> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        query.setParameter("email", user.getEmail());

        query.addScalar("name", StandardBasicTypes.STRING); // name chinh la r.name trong bang roles, String là kiểu của role name
        query.setResultTransformer(Transformers.aliasToBean(Role.class));

        return query.stream().toList();
    }
}
