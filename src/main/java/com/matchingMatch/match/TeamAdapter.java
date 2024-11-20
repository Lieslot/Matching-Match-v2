package com.matchingMatch.match;

import com.matchingMatch.match.domain.repository.TeamRepository;
import com.matchingMatch.match.exception.UnauthorizedAccessException;
import com.matchingMatch.team.domain.entity.Team;
import com.matchingMatch.team.domain.entity.TeamEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TeamAdapter {

    private final TeamRepository teamRepository;


    public Team getTeamBy(Long teamId) {

        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(UnauthorizedAccessException::new);

        return team.toDomain();
    }

    public TeamEntity getTeamEntityBy(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(UnauthorizedAccessException::new);
    }

    public Boolean existsById(Long teamId) {
        return teamRepository.existsById(teamId);
    }

    public List<Team> getUserTeams (Long userId) {
        List<TeamEntity> teams = teamRepository.findAllByLeaderId(userId);

        return teams.stream()
                .map(TeamEntity::toDomain)
                .toList();
    }


}
