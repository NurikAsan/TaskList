package com.nurikov.tasklist.repository.impl;

import com.nurikov.tasklist.domain.exception.ResourceMappingException;
import com.nurikov.tasklist.domain.task.Task;
import com.nurikov.tasklist.repository.DataSourceConfig;
import com.nurikov.tasklist.repository.TaskRepository;
import com.nurikov.tasklist.repository.mappers.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {
    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = """
            SELECT t.id as task_id, t.title as task_title, 
            t.description as task_description, t.status as task_status,
            t.expiration_date as task_expiration_date
            from tasks t
            where id=?
    """;
    private final String FIND_ALL_BY_USER_ID = """
            SELECT t.id as task_id, t.title as task_title,
            t.description as task_description, t.status as task_status,
            t.expiration_date as task_expiration_date
            FROM tasks t
            JOIN users_tasks ut on t.id=ut.task_id
            where ut.user_id=?
            """;
    private final String assignToUserId = "insert into users_tasks(task_id, user_id) values(?,?)";
    private final String update = """
            UPDATE tasks set title=?, description=?,
            expiration_date=?, status=?
            where id=?
            """;
    private final String create = "INSERT INTO tasks(title, description, expiration_date, status) values(?,?,?,?)";
    private final String delete = "DELETE FROM tasks where id=?";

    @Override
    public Optional<Task> findById(Long id) {
        try{
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            try(ResultSet resultset = statement.executeQuery()){
                return Optional.ofNullable(TaskRowMapper.mapRow(resultset));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error mapping findById");
        }
    }

    @Override
    public List<Task> findAllByUserId(Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_USER_ID);
            statement.setLong(1, userId);
            try (ResultSet resultset = statement.executeQuery()) {
                return TaskRowMapper.mapRows(resultset);
            }
        }
        catch (SQLException e) {
            throw new ResourceMappingException("Error mapping findAllByUserId");
        }
    }

    @Override
    public void assignToUserId(Long taskId, Long userId) {
        try{
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(assignToUserId);
            statement.setLong(1, taskId);
            statement.setLong(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error mapping assign");
        }
    }

    @Override
    public void update(Task task) {
        try{
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, task.getTitle());

            if(task.getDescription() == null)
                statement.setNull(2, Types.VARCHAR);
            else
                statement.setString(2, task.getDescription());
            if(task.getExpirationDate() == null)
                statement.setNull(3, Types.TIMESTAMP);
            else
                statement.setTimestamp(3, Timestamp.valueOf(task.getExpirationDate()));

            statement.setString(4, task.getStatus().name());
            statement.setLong(5, task.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error mapping update");
        }
    }

    @Override
    public void create(Task task) {
        try{
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(create,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, task.getTitle());

            if(task.getDescription() == null)
                statement.setNull(2, Types.VARCHAR);
            else
                statement.setString(2, task.getDescription());
            if(task.getExpirationDate() == null)
                statement.setNull(3, Types.TIMESTAMP);
            else
                statement.setTimestamp(3, Timestamp.valueOf(task.getExpirationDate()));

            statement.setString(4, task.getStatus().name());
            statement.executeUpdate();
            try(var resultSet = statement.getGeneratedKeys()){
                resultSet.next();
                task.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error mapping create");
        }
    }

    @Override
    public void delete(Long id) {
        try{
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(delete);
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error mapping delete");
        }
    }
}
