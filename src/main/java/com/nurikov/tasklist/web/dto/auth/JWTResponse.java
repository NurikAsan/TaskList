package com.nurikov.tasklist.web.dto.auth;

import lombok.Data;

@Data
public class JWTResponse {
    private long id;
    private String username;
    private String accessToken;
    private String refreshToken;
}
