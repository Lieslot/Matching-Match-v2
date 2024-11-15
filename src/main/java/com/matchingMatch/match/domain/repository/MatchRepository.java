package com.matchingMatch.match.domain.repository;

import com.matchingMatch.match.domain.entity.MatchEntity;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MatchRepository extends JpaRepository<MatchEntity, Long> {

    @Query("SELECT m "
            + "FROM match as m "
            + "where :matchId < m.id "
            + "order by m.id asc")
    List<MatchEntity> findPagesById(@Param("matchId") Long matchId, Pageable pageable);
}
