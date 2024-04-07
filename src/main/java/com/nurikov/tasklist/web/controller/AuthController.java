package com.nurikov.tasklist.web.controller;

import com.nurikov.tasklist.service.AuthService;
import com.nurikov.tasklist.service.UserService;
import com.nurikov.tasklist.web.dto.auth.JWTRequest;
import com.nurikov.tasklist.web.dto.auth.JWTResponse;
import com.nurikov.tasklist.web.dto.user.UserDTO;
import com.nurikov.tasklist.web.dto.validation.OnCreate;
import com.nurikov.tasklist.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
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
        var user = userService.create(userMapper.toUser(userDTO));
        return userMapper.toDto(user);
    }

    @PostMapping("/refresh")
    public JWTResponse refresh(@RequestBody String refreshToken){
        return authService.refresh(refreshToken);
    }
}
