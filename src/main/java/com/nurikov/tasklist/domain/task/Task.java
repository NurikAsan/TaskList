package com.nurikov.tasklist.domain.task;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Task implements Serializable {
    private long id;
    private String title;
    private String description;
    private Status status;
    private LocalDateTime expirationDate;
}
