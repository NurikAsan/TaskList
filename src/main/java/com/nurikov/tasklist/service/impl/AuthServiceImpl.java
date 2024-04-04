package com.nurikov.tasklist.service.impl;

import com.nurikov.tasklist.service.AuthService;
import com.nurikov.tasklist.web.dto.auth.JWTRequest;
import com.nurikov.tasklist.web.dto.auth.JWTResponse;

public class AuthServiceImpl implements AuthService {
    @Override
    public JWTResponse login(JWTRequest loginRequest) {
        return null;
    }

    @Override
    public JWTResponse refresh(String refreshToken) {
        return null;
    }
}
