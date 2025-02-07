package com.matchingMatch.match.domain.entity;

import java.util.Objects;

import com.matchingMatch.match.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "match_request")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchRequestEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long sendTeamId;
	@Column(nullable = false)
	private Long matchId;
	@Column(nullable = false)
	private Long targetTeamId;

	@Builder
	public MatchRequestEntity(Long sendTeamId, Long matchId, Long targetTeamId) {
		this.sendTeamId = sendTeamId;
		this.matchId = matchId;
		this.targetTeamId = targetTeamId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		MatchRequestEntity other = (MatchRequestEntity)obj;

		return other.sendTeamId.equals(sendTeamId) && other.matchId.equals(matchId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(sendTeamId, matchId);
	}



}
