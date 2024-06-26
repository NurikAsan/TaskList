package com.nurikov.tasklist.web.controller;

import com.nurikov.tasklist.service.AuthService;
import com.nurikov.tasklist.service.UserService;
import com.nurikov.tasklist.web.dto.auth.JWTRequest;
import com.nurikov.tasklist.web.dto.auth.JWTResponse;
import com.nurikov.tasklist.web.dto.user.UserDTO;
import com.nurikov.tasklist.web.dto.validation.OnCreate;
import com.nurikov.tasklist.web.mapper.UserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Auth Controller", description = "Auth API")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public JWTResponse login(@Validated @RequestBody JWTRequest loginRequest){
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public UserDTO register(@Validated(OnCreate.class) @RequestBody UserDTO userDTO){
        var user = userService.create(userMapper.toEntity(userDTO));
        return userMapper.toDto(user);
    }

    @PostMapping("/refresh")
    public JWTResponse refresh(@RequestBody String refreshToken){
        return authService.refresh(refreshToken);
    }
}
