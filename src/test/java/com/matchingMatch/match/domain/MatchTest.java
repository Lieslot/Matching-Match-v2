package com.matchingMatch.match.domain;

import com.matchingMatch.TestDataBuilder;
import com.matchingMatch.match.exception.MatchAlreadyConfirmedException;
import com.matchingMatch.team.domain.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class MatchTest {


    private TestDataBuilder testDataBuilder = new TestDataBuilder();
    private static final LocalDateTime MATCH_CONFIRMED_TIME = LocalDateTime.of(2021, 1, 1, 0, 0, 0);

    @Test
    void confirm_the_match() {
        // given

        Team host = testDataBuilder.createDefaultTeam(1L, 1L);
        Match match = testDataBuilder.createDefaultMatch(1L, host);

        Team participant = testDataBuilder.createDefaultTeam(2L, 2L);

        // when
        match.confirmMatch(participant);

        // then
        Assertions.assertThatExceptionOfType(MatchAlreadyConfirmedException.class)
                .isThrownBy(match::checkAlreadyConfirmed);
    }

    @Test
    void match_is_already_confirmed_exception() {
        // given
        Team host = testDataBuilder.createDefaultTeam(1L, 1L);
        Team participant = testDataBuilder.createDefaultTeam(2L, 2L);
        Match match = testDataBuilder.createConfirmedMatch(1L, host, participant, MATCH_CONFIRMED_TIME);
        // when then
        Assertions.assertThatExceptionOfType(MatchAlreadyConfirmedException.class)
                .isThrownBy(match::checkAlreadyConfirmed);
    }

    @Test
    void cancel_confirmed_match() {

        // given
        Team host = testDataBuilder.createDefaultTeam(1L, 1L);
        Team participant = testDataBuilder.createDefaultTeam(2L, 2L);
        Match match = testDataBuilder.createConfirmedMatch(1L, host, participant, MATCH_CONFIRMED_TIME);
        // when
        match.cancelMatch();
        // then
        Assertions.assertThat(match.getParticipantId()).isNull();
        Assertions.assertThat(match.getConfirmedTime()).isNull();
    }

    @Test
    void check_cancel_deadline() {
        // given
        Team host = testDataBuilder.createDefaultTeam(1L, 1L);
        Team participant = testDataBuilder.createDefaultTeam(2L, 2L);
        Match match = testDataBuilder.createConfirmedMatch(1L, host, participant, MATCH_CONFIRMED_TIME);
        // when
        Throwable throwable = Assertions.catchThrowable(() -> match.checkCancelDeadline(MATCH_CONFIRMED_TIME.plusMinutes(9)));
        Throwable throwableBorder = Assertions.catchThrowable(() -> match.checkCancelDeadline(MATCH_CONFIRMED_TIME.plusMinutes(10)));
        Throwable throwable2 = Assertions.catchThrowable(() -> match.checkCancelDeadline(MATCH_CONFIRMED_TIME.plusMinutes(11)));
        // then
        Assertions.assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThat(throwable2).isNull();
        Assertions.assertThat(throwableBorder).isNull();
    }




}
