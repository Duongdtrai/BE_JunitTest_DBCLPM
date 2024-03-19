package com.example.junit_test.modules.user.repository;

import com.example.junit_test.modules.user.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Integer countByToken(String token);

    void deleteByUserIdAndToken(Long userId, String token);
}
