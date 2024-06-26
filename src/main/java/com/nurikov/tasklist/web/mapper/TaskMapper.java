package com.nurikov.tasklist.web.mapper;

import com.nurikov.tasklist.domain.task.Task;
import com.nurikov.tasklist.web.dto.task.TaskDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper extends Mappable<Task, TaskDTO>{

}