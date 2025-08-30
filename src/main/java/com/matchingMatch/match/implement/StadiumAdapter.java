package com.matchingMatch.match.implement;

import com.matchingMatch.match.domain.entity.StadiumEntity;
import com.matchingMatch.match.domain.repository.StadiumRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StadiumAdapter {

	private final StadiumRepository stadiumRepository;

	public StadiumEntity getStadiumEntityBy(Long stadiumId) {
		return stadiumRepository.findById(stadiumId)
			.orElseThrow(() -> new IllegalArgumentException("해당 경기장을 찾을 수 없습니다."));
	}

	public List<StadiumEntity> getAllStadiumsBy(List<Long> stadiumIds) {
		return stadiumRepository.findAllById(stadiumIds);
	}
}
