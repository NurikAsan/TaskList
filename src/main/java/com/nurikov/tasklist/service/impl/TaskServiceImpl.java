package com.nurikov.tasklist.service.impl;

import com.nurikov.tasklist.domain.task.Task;
import com.nurikov.tasklist.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Override
    public Task getById(long id) {
        return null;
    }

    @Override
    public List<Task> getAllByUserId(long userId) {
        return null;
    }

    @Override
    public Task update(Task task) {
        return null;
    }

    @Override
    public Task create(Task task) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
