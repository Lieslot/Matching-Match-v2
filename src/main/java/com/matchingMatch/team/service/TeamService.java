package com.matchingMatch.team.service;


import com.matchingMatch.match.TeamAdapter;
import com.matchingMatch.team.domain.entity.Team;
import com.matchingMatch.team.domain.entity.TeamEntity;
import com.matchingMatch.match.domain.repository.TeamRepository;
import com.matchingMatch.match.dto.TeamProfileResponse;
import com.matchingMatch.match.dto.TeamProfileUpdateRequest;
import java.util.Optional;

import com.matchingMatch.team.dto.TeamRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamService {


    private final TeamAdapter teamAdapter;

    @Transactional
    public TeamProfileResponse getTeamProfile(Long teamId) {
        Team team = teamAdapter.getTeamBy(teamId);


        return TeamProfileResponse.builder()
                .teamName(team.getName())
                .teamLogoUrl(team.getLogoUrl())
                .mannerPoint(team.getMannerPoint())
                .region(team.getRegion())
                .gender(team.getGender())
                .teamDescription(team.getDescription())
                .build();
    }

    public Long registerTeam(TeamRegisterRequest teamRegisterRequest, Long leaderId) {

        Team team = Team.builder()
                .name(teamRegisterRequest.getName())
                .teamDescription(teamRegisterRequest.getDescription())
                .teamLogoUrl(teamRegisterRequest.getLogoUrl())
                .leaderId(leaderId)
                .region(teamRegisterRequest.getRegion())
                .build();

        return teamAdapter.save(team);
    }

    @Transactional
    public void updateTeamProfile(TeamProfileUpdateRequest teamProfileUpdateRequest, Long userId) {

        Long teamId = teamProfileUpdateRequest.getId();
        Team team = teamAdapter.getTeamBy(teamId);

        team.checkLeader(userId);

        team.setDescription(teamProfileUpdateRequest.getTeamDescription());
        team.setName(teamProfileUpdateRequest.getTeamName());
        team.setGender(teamProfileUpdateRequest.getGender());
        team.setRegion(teamProfileUpdateRequest.getRegion());
        team.setLogoUrl(teamProfileUpdateRequest.getTeamLogoUrl());
    }

    public void deleteTeam(Long id) {
        Team team = teamAdapter.getTeamBy(id);
        team.checkLeader(id);

        teamAdapter.delete(id);
    }
}
