package com.matchingMatch.match.domain;

import com.matchingMatch.match.TestDataBuilder;
import com.matchingMatch.match.exception.MatchAlreadyConfirmedException;
import com.matchingMatch.team.domain.entity.Team;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.math.BigDecimal;

public class MatchTest {


    private TestDataBuilder testDataBuilder = new TestDataBuilder();


    @Test
    void confirm_the_match_post() {
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
        Match match = testDataBuilder.createConfirmedMatch(1L, host, participant);
        // when then
        Assertions.assertThatExceptionOfType(MatchAlreadyConfirmedException.class)
                .isThrownBy(match::checkAlreadyConfirmed);
    }


    @Test
    void cancel_confirmed_request() {
        // given
        Team host = testDataBuilder.createDefaultTeam(1L, 1L);
        Team participant = testDataBuilder.createDefaultTeam(2L, 2L);
        Match match = testDataBuilder.createConfirmedMatch(1L, host, participant);
        // when
        match.cancelMatch();
        // then
        Assertions.assertThat(match.getParticipant()).isNull();
    }


    @Test
    void cancel_confirmed_match() {

        // given
        Team host = testDataBuilder.createDefaultTeam(1L, 1L);
        Team participant = testDataBuilder.createDefaultTeam(2L, 2L);
        Match match = testDataBuilder.createConfirmedMatch(1L, host, participant);
        // when
        match.cancelMatch();
        // then
        Assertions.assertThat(match.getParticipant()).isNull();
    }

    @Test
    void rate_manner_point() {
        // given


        Team host = testDataBuilder.createDefaultTeam(1L, 1L);
        Team participant = testDataBuilder.createDefaultTeam(2L, 2L);
        Match match = testDataBuilder.createConfirmedMatch(1L, host, participant);

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

}
