package com.nurikov.tasklist.web.security.expression;

import com.nurikov.tasklist.domain.user.Role;
import com.nurikov.tasklist.service.UserService;
import com.nurikov.tasklist.web.security.JwtEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("customSecurityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression {
    private final UserService userService;

    public boolean canAccessUser(Long id){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        JwtEntity user = (JwtEntity) auth.getPrincipal();
        Long userId = user.getId();
        return userId.equals(id) || hasAnyRole(auth, Role.ROLE_ADMIN);
    }

    private boolean hasAnyRole(Authentication auth, Role... roles) {
        for(Role role: roles) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
            if (auth.getAuthorities().contains(authority))
                return true;
        }
        return false;
    }

    public boolean canAccessTask(Long taskId){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        JwtEntity user = (JwtEntity) auth.getPrincipal();
        Long userId = user.getId();
        return userService.isTaskOwner(userId, taskId);
    }
}
