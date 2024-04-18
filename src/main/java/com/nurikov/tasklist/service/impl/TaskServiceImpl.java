package com.nurikov.tasklist.service.impl;

import com.nurikov.tasklist.domain.exception.ResourceNotFoundException;
import com.nurikov.tasklist.domain.task.Status;
import com.nurikov.tasklist.domain.task.Task;
import com.nurikov.tasklist.domain.task.TaskImage;
import com.nurikov.tasklist.repository.TaskRepository;
import com.nurikov.tasklist.service.TaskService;
import com.nurikov.tasklist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ImageServiceImpl imageService;

    @Override
    @Cacheable(value = "TaskService::getById", key = "#id")
    public Task getById(long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found!"));
    }

    @Override
    public List<Task> getAllByUserId(long userId) {
        return taskRepository.findAllByUserId(userId);
    }

    @Transactional
    @Override
    @CachePut(value = "TaskService::getById", condition = "#task.id!=null", key = "#task.id")
    public Task update(Task task) {
        if(task.getStatus() == null)
            task.setStatus(Status.TODO);
        taskRepository.save(task);
        return task;
    }

    @Transactional
    @Override
    @Cacheable(value = "TaskService::getById", condition = "#task.id!=null", key = "#task.id")
    public Task create(Task task, long userId) {
        task.setStatus(Status.TODO);
        taskRepository.save(task);
        taskRepository.assignTask(userId, task.getId());
        return task;
    }

    @Transactional
    @Override
    @CacheEvict(value = "TaskService::getById", key = "#id")
    public void delete(long id) {
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "TaskService::getById", key = "#id")
    public void uploadImage(Long id, TaskImage taskImage) {
        Task task = getById(id);
        String image = imageService.upload(taskImage);
        task.getImages().add(image);
        taskRepository.save(task);
    }
}
