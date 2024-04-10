package com.nurikov.tasklist.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nurikov.tasklist.web.dto.validation.OnCreate;
import com.nurikov.tasklist.web.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Schema(description = "User DTO")
public class UserDTO {
    @Schema(description = "User Id", example = "1")
    @NotNull(message = "Id must be not null", groups = OnUpdate.class)
    private Long id;

    @Schema(description = "User name", example = "John Doe")
    @NotNull(message = "name must be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 150, message = "Length must be less then 150", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @Schema(description = "User username", example = "johndoe@gmail.com")
    @NotNull(message = "username must be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 150, message = "Length must be less then 150", groups = {OnCreate.class, OnUpdate.class})
    private String username;

    @Schema(description = "User password", example = "12345")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "password must be not null", groups = {OnCreate.class, OnUpdate.class})
    private String password;

    @Schema(description = "User passwordConfirm", example = "12345")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "password confirm must be not null", groups = {OnCreate.class})
    private String passwordConfirm;
}
