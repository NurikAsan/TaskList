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
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @QueryMapping(name = "tasksByUserId")
    @Operation(summary = "get all tasks user by id")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public List<TaskDTO> getTasksByUserId(@PathVariable @Argument Long id){
        List<Task> tasks = taskService.getAllByUserId(id);
        return taskMapper.toDto(tasks);
    }

    @PostMapping("/{id}/tasks")
    @MutationMapping(name = "createTask")
    @Operation(summary = "create task for user")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public TaskDTO create(@PathVariable @Argument Long id,
                          @Validated(OnCreate.class) @RequestBody @Argument TaskDTO taskDTO){
        var task = taskService.create(taskMapper.toEntity(taskDTO), id);
        return taskMapper.toDto(task);
    }

    @PutMapping
    @MutationMapping(name = "updateUser")
    @Operation(summary = "update user")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#userDTO.id)")
    public UserDTO update(@Validated(OnUpdate.class) @RequestBody @Argument UserDTO userDTO){
        var updatedUser = userService.update(userMapper.toEntity(userDTO));
        return userMapper.toDto(updatedUser);
    }

    @GetMapping("/{id}")
    @QueryMapping(name = "userById")
    @Operation(summary = "get user by id")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public UserDTO getById(@PathVariable @Argument Long id){
        var user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    @MutationMapping(name = "deleteUser")
    @Operation(summary = "delete user")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public void delete(@PathVariable @Argument Long id){
        userService.delete(id);
    }

}
