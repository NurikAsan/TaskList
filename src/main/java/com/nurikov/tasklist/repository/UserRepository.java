package com.nurikov.tasklist.repository;

import com.nurikov.tasklist.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    @Query(value = """
         SELECT exists (
            SELECT 1 
            FROM users_tasks
            where user_id=:userId and task_id=:taskId
         )
      """, nativeQuery = true
    )
    boolean isTaskOwner(@Param("userId") Long userId, @Param("taskId") Long taskId);
}
