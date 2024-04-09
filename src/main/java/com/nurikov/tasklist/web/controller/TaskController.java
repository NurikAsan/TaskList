package com.nurikov.tasklist.web.controller;

import com.nurikov.tasklist.service.TaskService;
import com.nurikov.tasklist.web.dto.task.TaskDTO;
import com.nurikov.tasklist.web.dto.validation.OnUpdate;
import com.nurikov.tasklist.web.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tasks")
@RequiredArgsConstructor
@Validated
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PutMapping
    public TaskDTO update(@Validated(OnUpdate.class) @RequestBody TaskDTO taskDTO){
        var updatedTask = taskService.update(taskMapper.toEntity(taskDTO));
        return taskMapper.toDto(updatedTask);
    }

    @GetMapping("/{id}")
    public TaskDTO getById(@PathVariable long id){
        var task = taskService.getById(id);
        return taskMapper.toDto(task);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        taskService.delete(id);
    }
}
