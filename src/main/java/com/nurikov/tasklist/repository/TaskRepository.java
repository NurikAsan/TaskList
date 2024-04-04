package com.nurikov.tasklist.repository;

import com.nurikov.tasklist.domain.task.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Optional<Task> findById(long id);
    List<Task> findAllByUserId(long userId);
    void assignToUserId(long taskId, long userId);
    void update(Task task);
    void create(Task task);
    void delete(long id);
}
