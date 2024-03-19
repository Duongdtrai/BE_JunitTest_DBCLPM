package com.example.junit_test.modules.user.repository;

import com.example.junit_test.modules.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM users WHERE email = :email AND status = true", nativeQuery = true)
    Optional<User> findByEmail(String email);

    User findByEmailAndStatus(String email, Boolean status);
}
