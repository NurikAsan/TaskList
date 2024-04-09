package com.nurikov.tasklist.service;


import com.nurikov.tasklist.web.dto.auth.JWTRequest;
import com.nurikov.tasklist.web.dto.auth.JWTResponse;

public interface AuthService {
    JWTResponse login(JWTRequest loginRequest);
    JWTResponse refresh(String refreshToken);
}
