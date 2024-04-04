package com.nurikov.tasklist.service;

public class AuthService {
    JWTResponce login(JWTRequest loginRequest);
    JWTResponce refresh(String refreshToken);
}
