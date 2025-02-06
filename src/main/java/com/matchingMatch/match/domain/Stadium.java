package com.matchingMatch.match.domain;

import com.matchingMatch.match.domain.enums.SeoulDistrict;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Stadium {
	private Long id;
	private String name;
	private SeoulDistrict district;
	private Boolean isParkPossible;
	private String address;

	@Builder
	public Stadium(Long id, String name, SeoulDistrict district, Boolean isParkPossible, String address) {
		this.id = id;
		this.name = name;
		this.district = district;
		this.isParkPossible = isParkPossible;
		this.address = address;
	}
}
