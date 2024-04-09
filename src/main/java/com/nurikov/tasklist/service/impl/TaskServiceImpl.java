package com.nurikov.tasklist.service.impl;

import com.nurikov.tasklist.domain.exception.ResourceNotFoundException;
import com.nurikov.tasklist.domain.task.Status;
import com.nurikov.tasklist.domain.task.Task;
import com.nurikov.tasklist.repository.TaskRepository;
import com.nurikov.tasklist.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public Task getById(long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found!"));
    }

    @Override
    public List<Task> getAllByUserId(long userId) {
        return taskRepository.findAllByUserId(userId);
    }

    @Transactional
    @Override
    public Task update(Task task) {
        if(task.getStatus() == null)
            task.setStatus(Status.TODO);
        taskRepository.update(task);
        return task;
    }

    @Transactional
    @Override
    public Task create(Task task, long userId) {
        task.setStatus(Status.TODO);
        taskRepository.create(task);
        taskRepository.assignToUserId(task.getId(), userId);
        return task;
    }

    @Transactional
    @Override
    public void delete(long id) {
        taskRepository.delete(id);
    }
}
