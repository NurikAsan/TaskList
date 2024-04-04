package com.nurikov.tasklist.web.controller;

import com.nurikov.tasklist.domain.task.Task;
import com.nurikov.tasklist.service.TaskService;
import com.nurikov.tasklist.service.UserService;
import com.nurikov.tasklist.web.dto.task.TaskDTO;
import com.nurikov.tasklist.web.dto.user.UserDTO;
import com.nurikov.tasklist.web.dto.validation.OnCreate;
import com.nurikov.tasklist.web.dto.validation.OnUpdate;
import com.nurikov.tasklist.web.mapper.TaskMapper;
import com.nurikov.tasklist.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping("/{id}/tasks")
    public List<TaskDTO> getTasksByUserId(@PathVariable long id){
        List<Task> tasks = taskService.getAllByUserId(id);
        return taskMapper.toDTO(tasks);
    }

    @PostMapping("/{id}/tasks")
    public TaskDTO create(@PathVariable long id,
                          @Validated(OnCreate.class) @RequestBody TaskDTO taskDTO){
        var task = taskService.create(taskMapper.toTask(taskDTO));
        return taskMapper.toDTO(task);
    }

    @PutMapping
    public TaskDTO update(@Validated(OnUpdate.class) @RequestBody TaskDTO taskDTO){
        var updatedTask = taskService.update(taskMapper.toTask(taskDTO));
        return taskMapper.toDTO(updatedTask);
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable long id){
        var user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        userService.delete(id);
    }

}
