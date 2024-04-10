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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User Controller", description = "User API")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping("/{id}/tasks")
    @Operation(summary = "get all tasks user by id")
    public List<TaskDTO> getTasksByUserId(@PathVariable long id){
        List<Task> tasks = taskService.getAllByUserId(id);
        return taskMapper.toDto(tasks);
    }

    @PostMapping("/{id}/tasks")
    @Operation(summary = "create task for user")
    public TaskDTO create(@PathVariable long id,
                          @Validated(OnCreate.class) @RequestBody TaskDTO taskDTO){
        var task = taskService.create(taskMapper.toEntity(taskDTO), id);
        return taskMapper.toDto(task);
    }

    @PutMapping
    @Operation(summary = "update user")
    public UserDTO update(@Validated(OnUpdate.class) @RequestBody UserDTO userDTO){
        var updatedUser = userService.update(userMapper.toEntity(userDTO));
        return userMapper.toDto(updatedUser);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get user by id")
    public UserDTO getById(@PathVariable long id){
        var user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete user")
    public void delete(@PathVariable long id){
        userService.delete(id);
    }

}
