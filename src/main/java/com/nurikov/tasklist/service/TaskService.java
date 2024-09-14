package com.nurikov.tasklist.service;



import com.nurikov.tasklist.domain.task.Task;
import com.nurikov.tasklist.domain.task.TaskImage;

import java.time.Duration;
import java.util.List;

public interface TaskService {
    Task getById(long id);
    List<Task> getAllByUserId(long userId);
    List<Task> getAllSoonTasks(Duration duration);
    Task update(Task task);
    Task create(Task task, long userId);
    void delete(long id);
    void uploadImage(Long id, TaskImage taskImage);
}
