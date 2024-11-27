package com.matchingMatch.match.domain.repository;

import com.matchingMatch.match.domain.entity.MatchEntity;
import java.util.List;
import java.util.Optional;

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
    List<MatchEntity> findAllByParticipantId(Long participantId);
    void deleteAllByHostId(Long hostId);


    List<MatchEntity> findAllByHostId(Long hostId);
}
