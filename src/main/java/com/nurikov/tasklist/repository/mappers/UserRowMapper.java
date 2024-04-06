package com.nurikov.tasklist.repository.mappers;

import com.nurikov.tasklist.domain.task.Task;
import com.nurikov.tasklist.domain.user.Role;
import com.nurikov.tasklist.domain.user.User;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRowMapper {

    @SneakyThrows
    public static User mapRow(ResultSet resultSet){
        Set<Role> roles = new HashSet<>();
        while (resultSet.next())
            roles.add(Role.valueOf(resultSet.getString("users_role_rol")));
        resultSet.beforeFirst();
        List<Task> tasks = TaskRowMapper.mapRows(resultSet);
        resultSet.beforeFirst();
        if(resultSet.next()){
            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setName(resultSet.getString("user_name"));
            user.setUsername(resultSet.getString("user_username"));
            user.setPassword(resultSet.getString("user_password"));
            user.setRole(roles);
            user.setTask(tasks);
            return user;
        }
        return null;
    }
}