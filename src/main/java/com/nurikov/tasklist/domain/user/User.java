package com.nurikov.tasklist.domain.user;

import lombok.Data;
import org.springframework.scheduling.config.Task;

import java.util.List;
import java.util.Set;

@Data
public class User {

    private Long id;
    private String username;
    private String password;
    private String passwordConfirm;
    private Set<Role> role;
    private List<Task> task;

}
