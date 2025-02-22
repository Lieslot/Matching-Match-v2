package com.matchingMatch.match.service;

import com.matchingMatch.match.domain.repository.MatchRepository;
import com.matchingMatch.match.domain.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MatchBookmarkService {

	private final TeamRepository teamRepository;
	private final MatchRepository matchRepository;

	@Transactional
	public void addMatchBookmark(Long teamId, Long matchId) {

	}

	@Transactional
	public void removeMatchBookmark(Long matchBookmarkId, Long teamId) {

	}

}
