package com.matchingMatch.match;

import com.matchingMatch.match.domain.repository.TeamRepository;
import com.matchingMatch.match.exception.UnauthorizedAccessException;
import com.matchingMatch.team.domain.entity.LeaderRequestEntity;
import com.matchingMatch.team.domain.entity.Team;
import com.matchingMatch.team.domain.entity.TeamEntity;
import com.matchingMatch.team.domain.repository.LeaderRequestRepository;
import com.matchingMatch.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TeamAdapter {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final LeaderRequestRepository leaderRequestRepository;

    public Team getTeamBy(Long teamId) {

        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(UnauthorizedAccessException::new);

        return team.toDomain();
    }

//    public Team getTeamBy(String username) {
//        UserDetail userDetail = userRepository.findByUsername(username)
//                .orElseThrow(IllegalArgumentException::new);
//        TeamEntity teamEntity  = teamRepository.findAllByLeaderId(username)
//                .orElseThrow(IllegalArgumentException::new);
//        return teamEntity.toDomain();
//    }

    public TeamEntity getTeamEntityBy(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(UnauthorizedAccessException::new);
    }

    public Boolean existsById(Long teamId) {
        return teamRepository.existsById(teamId);
    }

    public Long save(Team team) {
        return teamRepository.save(TeamEntity.of(team)).getId();
    }

    public void save(LeaderRequestEntity leaderRequestEntity) {
        leaderRequestRepository.save(leaderRequestEntity);
    }

    public void delete(Long teamId) {
        teamRepository.deleteById(teamId);
    }

    public LeaderRequestEntity getLeaderRequestByTeamID(Long teamId) {
        return leaderRequestRepository.findByTeamId(teamId)
                .orElseThrow(IllegalArgumentException::new);
    }

    public void deleteLeaderRequestByTeamId(Long teamId) {
        leaderRequestRepository.deleteByTeamId(teamId);
    }

    public Long countUserTeam(Long userId) {
         return teamRepository.countAllByLeaderId(userId);
    }
    public Team getTeamByLeaderId(Long userId) {
        TeamEntity teamEntity = teamRepository.findByLeaderId(userId)
                .orElseThrow(IllegalArgumentException::new);
        return teamEntity.toDomain();
    }
}
