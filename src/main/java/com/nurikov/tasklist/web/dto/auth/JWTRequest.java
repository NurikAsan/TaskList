package com.nurikov.tasklist.web.dto.auth;

import lombok.Data;

@Data
public class JWTRequest {
    private String username;
    private String password;
}
