package com.nurikov.tasklist.domain.user;

import com.nurikov.tasklist.domain.task.Task;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name="tasks")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String name;
    private String password;

    @Transient
    private String passwordConfirm;

    @CollectionTable(name = "users_roles")
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Role> role;

    @CollectionTable(name = "users_tasks")
    @OneToMany
    @JoinColumn(name = "task_id")
    private List<Task> task;
}
