package com.matchingMatch.match.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchRequestThumbnail {

	private Long id;
	private Long targetTeamId;
	private String targetTeamLogoUrl;
	private Long matchId;
	private String targetTeamName;

	@Builder
	public MatchRequestThumbnail(Long id, Long targetTeamId, Long matchId, String targetTeamName,
		String targetTeamLogoUrl) {
		this.id = id;
		this.targetTeamId = targetTeamId;
		this.targetTeamLogoUrl = targetTeamLogoUrl;
		this.matchId = matchId;
		this.targetTeamName = targetTeamName;

	}
}
