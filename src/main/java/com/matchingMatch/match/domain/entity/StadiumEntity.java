package com.matchingMatch.match.domain.entity;

import com.matchingMatch.match.domain.BaseEntity;
import com.matchingMatch.match.domain.Stadium;
import com.matchingMatch.match.domain.enums.SeoulDistrict;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Entity(name = "stadium")
@Getter
public class StadiumEntity extends BaseEntity {

	public StadiumEntity() {

	}

	@Builder
	public StadiumEntity(String name, SeoulDistrict district, Boolean isParkPossible, String address) {
		this.name = name;
		this.district = district;
		this.isParkPossible = isParkPossible;
		this.address = address;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private SeoulDistrict district;
	@Column(nullable = false)
	private Boolean isParkPossible;
	@Column(nullable = false)
	private String address;

	public Stadium toDomain() {
		return Stadium.builder()
			.name(name)
			.district(district)
			.isParkPossible(isParkPossible)
			.address(address)
			.build();
	}

}
