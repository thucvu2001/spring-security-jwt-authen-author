package com.example.security.repository;

import com.example.security.entity.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiRepository extends JpaRepository<Api, Long> {

    @Query(value = "SELECT * FROM api WHERE api_code IN :apiCodes", nativeQuery = true)
    List<Api> findAllByApiCodes(List<String> apiCode);
}
