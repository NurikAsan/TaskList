package com.nurikov.tasklist.repository.mappers;

import com.nurikov.tasklist.domain.task.Status;
import com.nurikov.tasklist.domain.task.Task;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TaskRowMapper {

    @SneakyThrows
    public static Task mapRow(ResultSet resultSet){
        if(resultSet.next()) {
            Task task = new Task();
            task.setId(resultSet.getLong("id"));
            task.setTitle(resultSet.getString("title"));
            task.setDescription(resultSet.getString("description"));
            task.setStatus(Status.valueOf(resultSet.getString("status")));
            Timestamp timestamp = resultSet.getTimestamp("expiration_date");
            if (timestamp != null)
                task.setExpirationData(timestamp.toLocalDateTime());

            return task;
        }
        return null;
    }

    @SneakyThrows
    public static List<Task> mapRows(ResultSet resultSet){
        List<Task> tasks = new ArrayList<>();
        while(resultSet.next()) {
            Task task = new Task();
            task.setId(resultSet.getLong("id"));
            if (!resultSet.wasNull()) {
                task.setTitle(resultSet.getString("title"));
                task.setDescription(resultSet.getString("description"));
                task.setStatus(Status.valueOf(resultSet.getString("status")));
                Timestamp timestamp = resultSet.getTimestamp("expiration_date");
                if (timestamp != null)
                    task.setExpirationData(timestamp.toLocalDateTime());
                tasks.add(task);
            }
        }
        return tasks;
    }
}
