package com.nurikov.tasklist.service.impl;

import com.nurikov.tasklist.domain.exception.ResourceNotFoundException;
import com.nurikov.tasklist.domain.user.Role;
import com.nurikov.tasklist.domain.user.User;
import com.nurikov.tasklist.repository.UserRepository;
import com.nurikov.tasklist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
    @Cacheable(value = "UserService::getById", key = "#id")
    public User getById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));
    }

    @Override
    @Cacheable(value = "UserService::getByUsername", key = "#username")
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Transactional
    @Override
    @Caching(put= {
            @CachePut(value = "UserService::getById", key = "#user.id"),
            @CachePut(value = "UserService::getByUsername", key = "#user.username")
    })
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Transactional
    @Override
    @Caching(cacheable = {
            @Cacheable(value = "UserService::getById", condition = "#user.id!=null" ,key = "#user.id"),
            @Cacheable(value = "UserService::getByUsername", condition = "#user.username!=null", key = "#user.username")
    })
    public User create(User user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent())
            throw new IllegalStateException("User already exists");
        if(!user.getPassword().equals(user.getPasswordConfirm()))
            throw new IllegalStateException("Password incorrect");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = Set.of(Role.ROLE_USER);
        user.setRole(roles);
        userRepository.save(user);
        return user;
    }

    @Override
    public boolean isTaskOwner(long userId, long taskId) {
        return userRepository.isTaskOwner(userId, taskId);
    }

    @Transactional
    @Override
    @CacheEvict(value = "UserService::getById", key ="#id" )
    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
