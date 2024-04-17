package com.nurikov.tasklist.domain.task;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "tasks")
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @CollectionTable(name = "tasks_images")
    @Column(name = "image")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images;
}
