package com.matchingMatch.team.domain.entity;

import java.util.Objects;

import com.matchingMatch.match.domain.BaseEntity;
import com.matchingMatch.match.domain.enums.Gender;
import com.matchingMatch.team.domain.Team;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "team")
@Getter
@Setter
public class TeamEntity extends BaseEntity {

	public static TeamEntity of(Team team) {

		TeamEntityBuilder builder = TeamEntity.builder()
			.name(team.getName())
			.description(team.getDescription())
			.logoUrl(team.getLogoUrl())
			.leaderId(team.getLeaderId())
			.mannerPointSum(team.getMannerPointSum())
			.matchCount(team.getMatchCount())
			.region(team.getRegion())
			.gender(team.getGender());

		if (team.getId() != null) {
			builder.id(team.getId());
		}
		return builder.build();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String name;

	private String description;

	private String logoUrl;
	@Column(nullable = false)
	private Long leaderId;

	private Long mannerPointSum = 0L;

	private Long matchCount = 0L;

	@Column(nullable = false)
	private String region;

	@Column(nullable = false)
	private Gender gender;

	@Builder
	public TeamEntity(Long id, String name, String description, String logoUrl, Long mannerPointSum, Long matchCount,
		String region, Gender gender, Long leaderId) {
		this.id = id;
		this.name = name;
		this.matchCount = matchCount;
		this.mannerPointSum = mannerPointSum;
		this.description = description;
		this.logoUrl = logoUrl;
		this.region = region;
		this.gender = gender;
		this.leaderId = leaderId;

	}

	public TeamEntity() {

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TeamEntity team = (TeamEntity)o;
		return Objects.equals(id, team.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	// TODO 이미 평가한 경우 무시하도록 예외처리
	public void rate(Long mannerPoint) {
		mannerPointSum += mannerPoint;
		matchCount++;
	}

	public float calculateMannerPoint() {
		return (float)this.mannerPointSum / this.matchCount;
	}

	public Team toDomain() {
		return Team.builder()
			.id(id)
			.name(name)
			.teamDescription(description)
			.teamLogoUrl(logoUrl)
			.leaderId(leaderId)
			.mannerPointSum(mannerPointSum)
			.matchCount(matchCount)
			.region(region)
			.gender(gender)
			.build();
	}
}
