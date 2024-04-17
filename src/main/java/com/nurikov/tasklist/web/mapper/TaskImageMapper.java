package com.nurikov.tasklist.web.mapper;

import com.nurikov.tasklist.domain.task.TaskImage;
import com.nurikov.tasklist.web.dto.task.TaskImageDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskImageMapper extends Mappable<TaskImage, TaskImageDTO> {
}
