package com.nurikov.tasklist.repository.impl;

import com.nurikov.tasklist.domain.exception.ResourceMappingException;
import com.nurikov.tasklist.domain.user.Role;
import com.nurikov.tasklist.domain.user.User;
import com.nurikov.tasklist.repository.DataSourceConfig;
import com.nurikov.tasklist.repository.UserRepository;
import com.nurikov.tasklist.repository.mappers.UserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = """
            select u.id as user_id, u.name as user_name, u.username as user_username,
            u.password as user_password, ur.role as user_role,
            t.id as task_id, t.title as task_title, t.description as task_description, t.expiration_date as task_expiration_date,
            t.status as task_status
            from users u
            left join users_roles ur on u.id=ur.user_id
            left join users_tasks ut on u.id=ut.user_id
            left join tasks t on ut.task_id=t.id
            where u.id=?
            """;
    private final String FIND_BY_USERNAME = """
            select u.id as user_id, u.name as user_name, u.username as user_username,
            u.password as user_password, ur.role as user_role,
            t.id as task_id, t.title as task_title, t.description as task_description, t.expiration_date as task_expiration_date,
            t.status as task_status
            from users u
            left join users_roles ur on u.id=ur.user_id
            left join users_tasks ut on u.id=ut.user_id
            left join tasks t on ut.task_id=t.id
            where u.username=?
            """;
    private final String UPDATE = """
            Update users set name=?, username=?, password=?
            where id=?
            """;
    private final String CREATE = """
            insert into users(name, username, password)
            values(?,?,?)
            """;
    private final String insertUserRole = """
            insert into users_roles(user_id, role)
            values(?, ?)
            """;
    private final String  isTaskOwner = """
            select exists(
                select 1
                from users_tasks
                where user_id = ? and task_id = ?
            )
            """;
    private final String delete = "delete from users where id=?";

    @Override
    public Optional<User> findById(long id) {
        try{
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, id);
            try(ResultSet resultset = statement.executeQuery()){
                return Optional.ofNullable(UserRowMapper.mapRow(resultset));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error mapping findById");
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try{
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, username);
            try(var resultset = statement.executeQuery()){
                return Optional.ofNullable(UserRowMapper.mapRow(resultset));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error mapping findByUsername");
        }
    }

    @Override
    public void update(User user) {
        try{
        Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setLong(4, user.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error mapping update user");
        }
    }

    @Override
    public void create(User user) {
        try{
        Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());

            statement.executeUpdate();
            try(var resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                user.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error mapping create user");
        }
    }

    @Override
    public void insertUserRole(Long userId, Role role) {
        try{
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(insertUserRole);
            statement.setLong(1, userId);
            statement.setString(2, role.name());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error mapping insertRole");
        }
    }

    @Override
    public boolean isTaskOwner(Long userId, Long taskId) {
        try{
        Connection connection = dataSourceConfig.getConnection();
            var statement = connection.prepareStatement(isTaskOwner);
            statement.setLong(1, userId);
            statement.setLong(2, taskId);

            try(var resultSet =  statement.executeQuery()){
                resultSet.next();
                return resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error mapping insertRole");
        }
    }

    @Override
    public void delete(long id) {
        try{
        Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(delete);
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error mapping insertRole");
        }
    }
}
