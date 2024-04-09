package com.nurikov.tasklist.service.impl;

import com.nurikov.tasklist.domain.exception.ResourceNotFoundException;
import com.nurikov.tasklist.domain.user.Role;
import com.nurikov.tasklist.domain.user.User;
import com.nurikov.tasklist.repository.UserRepository;
import com.nurikov.tasklist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Transactional
    @Override
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.update(user);
        return user;
    }

    @Transactional
    @Override
    public User create(User user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent())
            throw new IllegalStateException("User already exists");
        if(!user.getPassword().equals(user.getPasswordConfirm()))
            throw new IllegalStateException("Password incorrect");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.create(user);
        Set<Role> roles = Set.of(Role.ROLE_USER);
        userRepository.insertUserRole(user.getId(), Role.ROLE_USER);
        user.setRole(roles);
        return user;
    }

    @Override
    public boolean isTaskOwner(long userId, long taskId) {
        return userRepository.isTaskOwner(userId, taskId);
    }

    @Transactional
    @Override
    public void delete(long id) {
        userRepository.delete(id);
    }
}
