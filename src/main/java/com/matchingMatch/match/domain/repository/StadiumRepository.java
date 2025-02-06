package com.matchingMatch.match.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matchingMatch.match.domain.entity.StadiumEntity;

public interface StadiumRepository extends JpaRepository<StadiumEntity, Long> {
}
