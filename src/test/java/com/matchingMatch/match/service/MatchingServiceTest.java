package com.matchingMatch.match.service;

import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.MatchRequest;
import com.matchingMatch.match.domain.Team;
import com.matchingMatch.match.domain.enums.Gender;
import com.matchingMatch.match.domain.enums.Role;
import com.matchingMatch.match.domain.repository.MatchRepository;
import com.matchingMatch.match.domain.repository.TeamRepository;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
public class MatchingServiceTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchService matchService;

    Match match;
    Team hostTeam;
    Team otherTeam;
    @BeforeEach
    void setUp() {
        hostTeam = Team.builder()
                .teamName("ss")
                .gender(Gender.FEMALE)
                .teamDescription("ddd")
                .account("dddd")
                .password("dddd")
                .role(Role.USER)
                .region("서울 관악구")
                .build();
        otherTeam = Team.builder()
                .teamName("sdw")
                .gender(Gender.FEMALE)
                .teamDescription("ddd")
                .account("sdw")
                .password("dddd")
                .role(Role.USER)
                .region("서울 관악구")
                .build();

        teamRepository.save(hostTeam);
        teamRepository.save(otherTeam);
        match = Match.builder()
                .host(hostTeam)
                .startTime(
                        LocalDateTime.of(2024, Month.MAY, 15, 20, 15))
                .endTime(LocalDateTime.of(2024, Month.MAY, 15, 20, 55))
                .stadiumCost(15000)
                .gender(Gender.FEMALE)
                .build();

        matchRepository.save(match);

    }

    @Transactional
    @Test
    void 매치_요청_저장_테스트() {
        
        matchService.addMatchRequest(match.getId(), otherTeam.getId());

        Set<MatchRequest> matchRequests = match.getMatchRequests();

        Assertions.assertThat(matchRequests).contains(new MatchRequest(otherTeam, match));

    }

    @Transactional
    @Test
    void 매치_요청_취소_테스트() {

        matchService.addMatchRequest(match.getId(), otherTeam.getId());
        Set<MatchRequest> matchRequests = match.getMatchRequests();
        Assertions.assertThat(matchRequests).contains(new MatchRequest(otherTeam, match));

        matchService.cancelMatchRequest(match.getId(), otherTeam.getId());
        Assertions.assertThat(matchRequests).doesNotContain(new MatchRequest(otherTeam, match));

    }

    @Transactional
    @Test
    void 매치_확정_테스트() {
        matchService.addMatchRequest(match.getId(), otherTeam.getId());
        matchService.confirmMatchRequest(match.getId(), hostTeam.getId(), otherTeam.getId());

        Team participant = match.getParticipant();

        Assertions.assertThat(match.getParticipant()).isEqualTo(participant);

    }

    @Transactional
    @Test
    void 참가자의_매치_확정_취소_테스트() {
        matchService.addMatchRequest(match.getId(), otherTeam.getId());
        matchService.confirmMatchRequest(match.getId(), hostTeam.getId(), otherTeam.getId());

        Team participant = match.getParticipant();

        Assertions.assertThat(match.getParticipant()).isEqualTo(participant);

        matchService.cancelConfirmedMatch(match.getId(), otherTeam.getId());

        Assertions.assertThat(match.getParticipant()).isNull();
    }

    @Transactional
    @Test
    void 주최자의_매치_확정_취소_테스트() {
        matchService.addMatchRequest(match.getId(), otherTeam.getId());
        matchService.confirmMatchRequest(match.getId(), hostTeam.getId(), otherTeam.getId());

        Team participant = match.getParticipant();

        Assertions.assertThat(match.getParticipant()).isEqualTo(participant);

        matchService.cancelConfirmedMatch(match.getId(), hostTeam.getId());

        Assertions.assertThat(match.getParticipant()).isNull();
    }




}
