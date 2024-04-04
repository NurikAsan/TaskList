package com.nurikov.tasklist.service;

import com.nurikov.tasklist.domain.user.User;
import org.springframework.stereotype.Service;

public interface UserService {
    User getById(long id);
    User getByUsername(String username);
    User update(User user);
    User create(User user);
    boolean isTaskOwner(long userId, long taskId);
    void delete(long id);
}
