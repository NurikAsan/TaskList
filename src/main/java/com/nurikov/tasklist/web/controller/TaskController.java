package com.nurikov.tasklist.web.controller;

import com.nurikov.tasklist.domain.task.TaskImage;
import com.nurikov.tasklist.service.TaskService;
import com.nurikov.tasklist.web.dto.task.TaskDTO;
//import com.nurikov.tasklist.web.dto.task.TaskImageDTO;
import com.nurikov.tasklist.web.dto.task.TaskImageDTO;
import com.nurikov.tasklist.web.dto.validation.OnUpdate;
import com.nurikov.tasklist.web.mapper.TaskImageMapper;
import com.nurikov.tasklist.web.mapper.TaskMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tasks")
@RequiredArgsConstructor
@Validated
@Tag(name = "Task Controller", description = "Task API")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final TaskImageMapper taskImageMapper;

    @PutMapping
    @Operation(summary = "update task")
    @PreAuthorize("canAccessTask(#taskDTO.id)")
    public TaskDTO update(@Validated(OnUpdate.class) @RequestBody TaskDTO taskDTO){
        var updatedTask = taskService.update(taskMapper.toEntity(taskDTO));
        return taskMapper.toDto(updatedTask);
    }

    @PostMapping("/{id}/image")
    @Operation(summary = "upload image")
    @PreAuthorize("canAccessTask(#id)")
    public void upload(@PathVariable Long id,
                       @Validated @ModelAttribute TaskImageDTO taskImageDto){
        TaskImage image = taskImageMapper.toEntity(taskImageDto);
        taskService.uploadImage(id, image);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get task by id")
    @PreAuthorize("canAccessTask(#id)")
    public TaskDTO getById(@PathVariable Long id){
        var task = taskService.getById(id);
        return taskMapper.toDto(task);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete task")
    @PreAuthorize("canAccessTask(#id)")
    public void delete(@PathVariable Long id){
        taskService.delete(id);
    }
}
