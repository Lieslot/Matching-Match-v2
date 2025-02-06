package com.matchingMatch.user.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matchingMatch.user.domain.UserDetail;

public interface UserRepository extends JpaRepository<UserDetail, Long> {
	Optional<UserDetail> findByUsername(String username);

	boolean existsByUsername(String username);
}
