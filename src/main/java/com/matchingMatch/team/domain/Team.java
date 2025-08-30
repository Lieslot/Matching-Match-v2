package com.matchingMatch.team.domain;

import static java.util.Objects.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.matchingMatch.match.domain.enums.Gender;
import com.matchingMatch.match.dto.TeamProfileUpdateRequest;
import com.matchingMatch.match.exception.UnauthorizedAccessException;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Team {

	private Long id;

	@NotNull
	private String name;

	private String description;
	private String logoUrl;

	@NotNull
	private Long leaderId;

	private Long mannerPointSum = 0L;
	private Long matchCount = 0L;
	@NotNull
	private String region;

	@NotNull
	private Gender gender;

	private List<Long> confirmedMatchIds = new ArrayList<>();

	@Builder
	public Team(Long id, Gender gender, String name, String teamDescription, String teamLogoUrl, Long leaderId,
		Long mannerPointSum, Long matchCount, String region, List<Long> confirmedMatchIds) {
		this.id = id;
		this.name = name;
		this.description = teamDescription;
		this.logoUrl = teamLogoUrl;
		this.leaderId = leaderId;
		this.mannerPointSum = mannerPointSum;
		this.matchCount = matchCount;
		this.region = region;
		this.confirmedMatchIds = confirmedMatchIds;
		this.gender = gender;

	}

	public void confirmMatch(Long matchId) {
		confirmedMatchIds.add(matchId);
	}

	public void cancelMatch(Long matchId) {
		confirmedMatchIds.remove(matchId);
	}

	public void rateMannerPoint(Long point) {
		mannerPointSum += point;
		matchCount++;
	}

	public BigDecimal getMannerPoint() {

		return BigDecimal.valueOf(mannerPointSum).divide(BigDecimal.valueOf(matchCount), 2, RoundingMode.HALF_UP);
	}

	public void checkLeader(Long leaderId) {
		if (!this.leaderId.equals(leaderId)) {
			throw new UnauthorizedAccessException();
		}
	}

	public boolean hasLeader(Long leaderId) {
		return this.leaderId.equals(leaderId);
	}

	public void updateTeamProfile(TeamProfileUpdateRequest teamProfileUpdateRequest) {
		this.name = requireNonNullElse(teamProfileUpdateRequest.getTeamName(), this.name);
		this.description = requireNonNullElse(teamProfileUpdateRequest.getTeamDescription(), this.description);
		this.logoUrl = requireNonNullElse(teamProfileUpdateRequest.getTeamLogoUrl(), this.logoUrl);
		this.region = requireNonNullElse(teamProfileUpdateRequest.getRegion(), this.region);
		this.gender = requireNonNullElse(teamProfileUpdateRequest.getGender(), this.gender);
	}

	public void changeLeader(Long targetUserId) {
		this.leaderId = targetUserId;
	}
}
