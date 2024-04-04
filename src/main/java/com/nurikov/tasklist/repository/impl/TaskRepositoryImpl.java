package com.nurikov.tasklist.repository.impl;

import com.nurikov.tasklist.domain.task.Task;
import com.nurikov.tasklist.repository.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepositoryImpl implements TaskRepository {
    @Override
    public Optional<Task> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Task> findAllByUserId(long userId) {
        return null;
    }

    @Override
    public void assignToUserId(long taskId, long userId) {

    }

    @Override
    public void update(Task task) {

    }

    @Override
    public void create(Task task) {

    }

    @Override
    public void delete(long id) {

    }
}
