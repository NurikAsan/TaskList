package com.nurikov.tasklist.service;



import com.nurikov.tasklist.domain.task.Task;

import java.util.List;

public interface TaskService {
    Task getById(long id);
    List<Task> getAllByUserId(long userId);
    Task update(Task task);
    Task create(Task task, long userId);
    void delete(long id);
}
