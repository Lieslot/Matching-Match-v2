package com.matchingMatch.match.service;


import com.matchingMatch.match.domain.Team;
import com.matchingMatch.match.domain.repository.TeamRepository;
import com.matchingMatch.match.dto.TeamProfileResponse;
import com.matchingMatch.match.dto.TeamProfileUpdateRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamService {


    private final TeamRepository teamRepository;

    @Transactional
    public TeamProfileResponse getTeamProfile(Long teamId) {
        Optional<Team> result = teamRepository.findById(teamId);
        if (result.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Team team = result.get();
        return TeamProfileResponse.builder()
                .teamName(team.getTeamName())
                .teamLogoUrl(team.getTeamLogoUrl())
                .mannerPoint(team.calculateMannerPoint())
                .region(team.getRegion())
                .gender(team.getGender())
                .teamDescription(team.getTeamDescription())
                .build();
    }

    @Transactional
    public void updateTeamProfile(Long teamId,
                                  TeamProfileUpdateRequest teamProfileUpdateRequest) {
        Optional<Team> result = teamRepository.findById(teamId);
        if (result.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Team team = result.get();

        team.setTeamDescription(teamProfileUpdateRequest.getTeamDescription());
        team.setTeamName(teamProfileUpdateRequest.getTeamName());
        team.setGender(teamProfileUpdateRequest.getGender());
        team.setRegion(teamProfileUpdateRequest.getRegion());
        team.setTeamLogoUrl(teamProfileUpdateRequest.getTeamLogoUrl());
    }

}
