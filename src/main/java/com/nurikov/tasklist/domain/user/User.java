package com.nurikov.tasklist.domain.user;

import com.nurikov.tasklist.domain.task.Task;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
public class User implements Serializable {

    private Long id;
    private String username;
    private String name;
    private String password;
    private String passwordConfirm;
    private Set<Role> role;
    private List<Task> task;

}
