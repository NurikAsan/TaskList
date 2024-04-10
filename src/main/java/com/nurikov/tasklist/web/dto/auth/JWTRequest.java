package com.nurikov.tasklist.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "JWTRequest")
public class JWTRequest {
    @Schema(description = "User username", example = "johndoe@gmail.com")
    @NotNull(message = "username must be not null")
    private String username;

    @Schema(description = "User password", example = "12345")
    @NotNull(message = "password must be not null")
    private String password;
}
