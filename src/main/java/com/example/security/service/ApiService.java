package com.example.security.service;

import com.example.security.entity.Api;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApiService {

    List<Api> findAllByApiCode(List<String> apiCodes);
}
