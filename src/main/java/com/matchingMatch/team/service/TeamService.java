package com.matchingMatch.team.service;


import com.matchingMatch.team.domain.entity.TeamEntity;
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
        Optional<TeamEntity> result = teamRepository.findById(teamId);
        if (result.isEmpty()) {
            throw new IllegalArgumentException();
        }

        TeamEntity team = result.get();
        return TeamProfileResponse.builder()
                .teamName(team.getName())
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
        Optional<TeamEntity> result = teamRepository.findById(teamId);
        if (result.isEmpty()) {
            throw new IllegalArgumentException();
        }
        TeamEntity team = result.get();

        team.setTeamDescription(teamProfileUpdateRequest.getTeamDescription());
        team.setName(teamProfileUpdateRequest.getTeamName());
        team.setGender(teamProfileUpdateRequest.getGender());
        team.setRegion(teamProfileUpdateRequest.getRegion());
        team.setTeamLogoUrl(teamProfileUpdateRequest.getTeamLogoUrl());
    }

}
