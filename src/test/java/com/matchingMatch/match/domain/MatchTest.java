package com.matchingMatch.match.domain;

import com.matchingMatch.match.TestDataBuilder;
import com.matchingMatch.match.exception.MatchAlreadyConfirmedException;
import com.matchingMatch.team.domain.entity.Team;
import net.bytebuddy.implementation.bytecode.Throw;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.math.BigDecimal;
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
        Assertions.assertThat(match.getParticipant()).isNull();
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

    @Test
    void rate_manner_point() {
        // given
        Team host = testDataBuilder.createDefaultTeam(1L, 1L);
        Team participant = testDataBuilder.createDefaultTeam(2L, 2L);
        Match match = testDataBuilder.createConfirmedMatch(1L, host, participant, MATCH_CONFIRMED_TIME);

        // when

        match.rateMannerPoint(new MannerRate(1L, 1L));
        match.rateMannerPoint(new MannerRate(2L, 1L));
        match.rateMannerPoint(new MannerRate(2L, 2L));
        // then

        Assertions.assertThat(match.getHost().getMannerPoint())
                .isCloseTo(BigDecimal.valueOf(1.5), Offset.offset(BigDecimal.valueOf(0.01)));
        Assertions.assertThat(match.getParticipant().getMannerPoint())
                .isCloseTo(BigDecimal.valueOf(1.0), Offset.offset(BigDecimal.valueOf(0.01)));
    }

    @Test
    void rate_manner_point_only_host() {
        // given
        Team host = testDataBuilder.createDefaultTeam(1L, 1L);
        Team participant = testDataBuilder.createDefaultTeam(2L, 2L);
        Match match = testDataBuilder.createConfirmedMatch(1L, host, participant, MATCH_CONFIRMED_TIME);

        // when
        match.rateMannerPoint(new MannerRate(1L, 1L));
        match.rateMannerPoint(new MannerRate(1L, 2L));
        match.rateMannerPoint(new MannerRate(1L, 3L));
        // then
        Assertions.assertThat(match.getIsHostRate()).isTrue();
        Assertions.assertThat(match.getIsParticipantRate()).isFalse();
        Assertions.assertThat(match.getParticipant().getMannerPoint())
                .isCloseTo(BigDecimal.valueOf(2.0), Offset.offset(BigDecimal.valueOf(0.01)));
    }

    @Test
    void rate_manner_point_only_participant() {
        // given
        Team host = testDataBuilder.createDefaultTeam(1L, 1L);
        Team participant = testDataBuilder.createDefaultTeam(2L, 2L);
        Match match = testDataBuilder.createConfirmedMatch(1L, host, participant, MATCH_CONFIRMED_TIME);

        // when
        match.rateMannerPoint(new MannerRate(2L, 1L));
        match.rateMannerPoint(new MannerRate(2L, 2L));
        match.rateMannerPoint(new MannerRate(2L, 3L));
        // then
        Assertions.assertThat(match.getIsParticipantRate()).isTrue();
        Assertions.assertThat(match.getIsHostRate()).isFalse();
        Assertions.assertThat(match.getHost().getMannerPoint())
                .isCloseTo(BigDecimal.valueOf(2.0), Offset.offset(BigDecimal.valueOf(0.01)));
    }


}
