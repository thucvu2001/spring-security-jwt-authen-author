package com.example.security.service;

import com.example.security.entity.Api;
import com.example.security.repository.ApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService{
    private final ApiRepository apiRepository;

    @Override
    public List<Api> findAllByApiCode(List<String> apiCodes) {
        return apiRepository.findAllByApiCodes(apiCodes);
    }
}
