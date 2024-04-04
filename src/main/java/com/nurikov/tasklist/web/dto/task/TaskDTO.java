package com.nurikov.tasklist.web.dto.task;

import com.nurikov.tasklist.domain.task.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDTO {
    private int id;
    private String title;
    private String description;
    private Status status;
    private LocalDateTime expirationData;
}
