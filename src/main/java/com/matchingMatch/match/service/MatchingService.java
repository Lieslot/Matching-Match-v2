package com.matchingMatch.match.service;

import com.matchingMatch.match.MannerRater;
import com.matchingMatch.match.MatchRequestAdapter;
import com.matchingMatch.match.MatchTeamValidator;
import com.matchingMatch.match.TeamAdapter;
import com.matchingMatch.match.domain.MannerRate;
import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.MatchAdapter;
import com.matchingMatch.match.domain.StadiumAdapter;
import com.matchingMatch.match.domain.entity.MatchRequestEntity;
import com.matchingMatch.listener.event.MatchRequestEvent;
import com.matchingMatch.match.domain.entity.StadiumEntity;
import com.matchingMatch.match.dto.MatchRequestDetail;
import com.matchingMatch.match.dto.MatchRequestThumbnail;
import com.matchingMatch.team.domain.entity.Team;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchingService {

	private static final Integer PAGE_SIZE = 20;

	private final MatchAdapter matchAdapter;
	private final MatchRequestAdapter matchRequestAdapter;
	private final MatchTeamValidator matchTeamValidator;
	private final MannerRater mannerRater;
	private final ApplicationEventPublisher eventPublisher;
	private final StadiumAdapter stadiumAdapter;

	private final TeamAdapter teamAdapter;

	public void sendMatchRequest(Long matchId, Long userId) {

		// TODO 이미 확정된 매치가 일정이 겹치는 경우 예외처리 추가

		Match match = matchAdapter.getMatchBy(matchId);
		Team sendTeam = teamAdapter.getTeamByLeaderId(userId);

		match.checkAlreadyConfirmed();

		MatchRequestEntity matchRequest = MatchRequestEntity.builder()
			.sendTeamId(sendTeam.getId())
			.matchId(matchId)
			.targetTeamId(match.getHostId())
			.build();
		matchRequestAdapter.save(matchRequest);

		eventPublisher.publishEvent(new MatchRequestEvent(matchId, match.getHostId(), sendTeam.getId()));

		// TODO: 매치 요청 알림 보내기
	}

	public void cancelMatchRequest(Long requestId, Long userId) {

		MatchRequestEntity matchRequest = matchRequestAdapter.findById(requestId);
		Match match = matchAdapter.getMatchBy(matchRequest.getMatchId());

		match.checkAlreadyConfirmed();
		matchTeamValidator.checkHost(match, userId);

		matchRequestAdapter.deleteById(requestId);

	}

	public void confirmMatchRequest(Long matchId, Long currentUserId, Long requestingTeamId) {

		Match match = matchAdapter.getMatchBy(matchId);
		Team requestingTeam = teamAdapter.getTeamBy(requestingTeamId);

		matchTeamValidator.checkHost(match, currentUserId);
		match.checkAlreadyConfirmed();

		match.confirmMatch(requestingTeam);

		matchAdapter.updateMatch(match);
		matchRequestAdapter.deleteAllByMatchId(matchId);

		// TODO 알림 보내기
	}

	public void cancelConfirmedMatch(Long matchId, Long currentUserId) {

		Match match = matchAdapter.getMatchBy(matchId);

		matchTeamValidator.checkHostOrParticipant(match, currentUserId);
		match.checkCancelDeadline(LocalDateTime.now());
		match.cancelMatch();

		matchAdapter.updateMatch(match);
	}

	public void refuseMatchRequest(Long matchRequestId, Long currentUserId) {

		MatchRequestEntity request = matchRequestAdapter.findById(matchRequestId);
		Match match = matchAdapter.getMatchBy(request.getMatchId());

		match.checkAlreadyConfirmed();
		matchTeamValidator.checkHost(match, currentUserId);

		matchRequestAdapter.deleteById(matchRequestId);
	}

	public void rateMannerPoint(Long matchId, MannerRate mannerRate) {

		Match match = matchAdapter.getMatchBy(matchId);
		Team team = teamAdapter.getTeamByLeaderId(mannerRate.userId());

		matchTeamValidator.checkHostOrParticipant(match, team);
		mannerRater.checkAlreadyRate(team, match);
		mannerRater.checkMatchEnd(match);

		mannerRater.rateMannerPoint(team, match, mannerRate);

		matchAdapter.updateMatch(match);
		matchAdapter.updateMannerRateCheck(match);
		teamAdapter.save(team);

	}

	// TODO: 페이징 추가
	public List<MatchRequestThumbnail> getRequestThumbnail(Long userId) {
		Team team = teamAdapter.getTeamByLeaderId(userId);

		List<MatchRequestEntity> requests = matchRequestAdapter.findAllByTargetTeamId(team.getId());

		List<Long> teamIds = requests.stream()
			.map(MatchRequestEntity::getTargetTeamId)
			.toList();

		Map<Long, Team> teams = teamAdapter.getAllTeamBy(teamIds).stream()
			.collect(Collectors.toMap(Team::getId, Function.identity()));

		return requests.stream()
			.map(r -> MatchRequestThumbnail.builder()
				.id(r.getId())
				.targetTeamName(teams.get(r.getTargetTeamId()).getName())
				.targetTeamId(r.getTargetTeamId())
				.targetTeamLogoUrl(teams.get(r.getTargetTeamId()).getLogoUrl())
				.matchId(r.getMatchId())
				.build())
			.toList();
	}

	public MatchRequestDetail getRequestDetail(Long matchId) {

		Match match = matchAdapter.getMatchBy(matchId);
		StadiumEntity stadium = stadiumAdapter.getStadiumEntityBy(match.getStadiumId());

		return MatchRequestDetail.builder()
			.targetTeamId(match.getParticipantId())
			.matchId(match.getId())
			.targetTeamName(teamAdapter.getTeamBy(match.getParticipantId()).getName())
			.startTime(match.getStartTime().toString())
			.endTime(match.getEndTime().toString())
			.stadiumName(stadium.getName())
			.stadiumAddress(stadium.getAddress())
			.build();
	}
}
