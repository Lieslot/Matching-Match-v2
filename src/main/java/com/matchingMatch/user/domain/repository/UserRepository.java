package com.matchingMatch.user.domain.repository;

import com.matchingMatch.user.domain.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDetail, Long> {
    Optional<UserDetail> findByUsername(String username);
    boolean existsByUsername(String username);
}
