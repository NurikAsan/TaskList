package com.nurikov.tasklist.service.impl;

import com.nurikov.tasklist.domain.user.User;
import com.nurikov.tasklist.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User getById(long id) {
        return null;
    }

    @Override
    public User getByUsername(String username) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public boolean isTaskOwner(long userId, long taskId) {
        return false;
    }

    @Override
    public void delete(long id) {

    }
}
