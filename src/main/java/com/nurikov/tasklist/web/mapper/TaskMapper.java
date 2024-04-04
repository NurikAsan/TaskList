package com.nurikov.tasklist.web.mapper;

import com.nurikov.tasklist.domain.task.Task;
import com.nurikov.tasklist.web.dto.task.TaskDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDTO toDTO(Task task);

    List<TaskDTO> toDTO(List<Task> tasks);

    Task task(TaskDTO taskDTO);
}
