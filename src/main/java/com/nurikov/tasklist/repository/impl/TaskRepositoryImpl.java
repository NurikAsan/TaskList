package com.nurikov.tasklist.repository.impl;

import com.nurikov.tasklist.domain.exception.ResourceMappingException;
import com.nurikov.tasklist.domain.task.Task;
import com.nurikov.tasklist.repository.DataSourceConfig;
import com.nurikov.tasklist.repository.TaskRepository;
import com.nurikov.tasklist.repository.mappers.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {
    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = "SELECT * from tasks where id=?";
    private final String FIND_ALL_BY_USER_ID = """
            SELECT t.id, t.title, t.description, t.status, t.expiration_date
            FROM tasks t
            JOIN users_tasks on t.id=users_tasks.task_id
            where user_id=?
            """;
    private final String assignToUserId = "insert into users_tasks(user_id, task_id) values(?,?)";
    private final String update = """
            UPDATE tasks set title=?, description=?,
            expiration_date=?, status=?
            where id=?
            """;
    private final String create = "INSERT INTO tasks(title, description, expiration_date, status) values(?,?,?,?)";
    private final String delete = "DELETE FROM tasks where id=?";

    @Override
    public Optional<Task> findById(long id) {
        var connection = dataSourceConfig.getConnection();
        try(connection){
            var statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            try(var resultset = statement.executeQuery()){
                return Optional.ofNullable(TaskRowMapper.mapRow(resultset));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error mapping findById");
        }
    }

    @Override
    public List<Task> findAllByUserId(long userId) {
        var connection = dataSourceConfig.getConnection();
        try(connection){
            var statement = connection.prepareStatement(FIND_ALL_BY_USER_ID);
            statement.setLong(1, userId);
            try(var resultset = statement.executeQuery()){
                return TaskRowMapper.mapRows(resultset);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error mapping findAllByUserId");
        }
    }

    @Override
    public void assignToUserId(long taskId, long userId) {
        var connection = dataSourceConfig.getConnection();
        try(connection){
            var statement = connection.prepareStatement(assignToUserId);
            statement.setLong(1, userId);
            statement.setLong(2, taskId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error mapping assign");
        }
    }

    @Override
    public void update(Task task) {
        var connection = dataSourceConfig.getConnection();
        try(connection){
            var statement = connection.prepareStatement(update);
            statement.setString(1, task.getTitle());

            if(task.getDescription() == null)
                statement.setNull(2, Types.VARCHAR);
            else
                statement.setString(2, task.getDescription());
            if(task.getExpirationData() == null)
                statement.setNull(3, Types.TIMESTAMP);
            else
                statement.setTimestamp(3, Timestamp.valueOf(task.getExpirationData()));

            statement.setString(4, task.getStatus().name());
            statement.setLong(5, task.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error mapping update");
        }
    }

    @Override
    public void create(Task task) {
        var connection = dataSourceConfig.getConnection();
        try(connection){
            var statement = connection.prepareStatement(create, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, task.getTitle());

            if(task.getDescription() == null)
                statement.setNull(2, Types.VARCHAR);
            else
                statement.setString(2, task.getDescription());
            if(task.getExpirationData() == null)
                statement.setNull(3, Types.TIMESTAMP);
            else
                statement.setTimestamp(3, Timestamp.valueOf(task.getExpirationData()));

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
    public void delete(long id) {
        var connection = dataSourceConfig.getConnection();
        try(connection){
            var statement = connection.prepareStatement(delete);
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error mapping delete");
        }
    }
}
