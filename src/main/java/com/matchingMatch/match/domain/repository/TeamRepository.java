package com.matchingMatch.match.domain.repository;

import com.matchingMatch.match.domain.Team;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByAccount(String account);
}
