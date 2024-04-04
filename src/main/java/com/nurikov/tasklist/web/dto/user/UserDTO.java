package com.nurikov.tasklist.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nurikov.tasklist.web.dto.validation.OnCreate;
import com.nurikov.tasklist.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserDTO {
    @NotNull(message = "Id must be not null", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "name must be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 150, message = "Length must be less then 150", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @NotNull(message = "username must be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 150, message = "Length must be less then 150", groups = {OnCreate.class, OnUpdate.class})
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "password must be not null", groups = {OnCreate.class, OnUpdate.class})
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "password confirm must be not null", groups = {OnCreate.class})
    private String passwordConfirm;
}
