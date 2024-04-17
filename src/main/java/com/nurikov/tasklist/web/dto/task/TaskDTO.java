package com.nurikov.tasklist.web.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nurikov.tasklist.domain.task.Status;
import com.nurikov.tasklist.web.dto.validation.OnCreate;
import com.nurikov.tasklist.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskDTO {
    @NotNull(message = "Id must be not null", groups = OnUpdate.class)
    private int id;

    @NotNull(message = "title must be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 150, message = "Length must be less then 150", groups = {OnCreate.class, OnUpdate.class})
    private String title;

    @Length(max = 150, message = "Length must be less then 150", groups = {OnCreate.class, OnUpdate.class})
    private String description;

    private Status status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime expirationDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> images;
}
