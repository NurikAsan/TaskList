package com.nurikov.tasklist.repository;



import com.nurikov.tasklist.domain.task.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Optional<Task> findById(Long id);
    List<Task> findAllByUserId(Long userId);
    void assignToUserId(@Param("taskId") Long taskId,@Param("userId") Long userId);
    void update(Task task);
    void create(Task task);
    void delete(Long id);
}
