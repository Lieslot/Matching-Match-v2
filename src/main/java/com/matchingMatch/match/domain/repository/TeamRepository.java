package com.matchingMatch.match.domain.repository;

import com.matchingMatch.team.domain.entity.TeamEntity;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
    List<TeamEntity> findAllByLeaderId(Long leaderId);
    Long countAllByLeaderId(Long leaderId);

}
