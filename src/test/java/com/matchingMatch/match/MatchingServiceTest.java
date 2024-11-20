package com.matchingMatch.match;


import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.exception.MatchAlreadyConfirmedException;
import com.matchingMatch.team.domain.entity.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MatchingServiceTest {


    private TestDataBuilder testDataBuilder;


    // TODO 매치가 이미 확정된 경우 예외처리
    void match_is_already_confirmed_exception() {
        // given
        Team host = testDataBuilder.createDefaultTeam(1L, 1L);
        Team participant = testDataBuilder.createDefaultTeam(2L, 2L);
        Match match = testDataBuilder.createConfirmedMatch(1L, host, participant);
        // when then
        Assertions.assertThatExceptionOfType(MatchAlreadyConfirmedException.class)
                .isThrownBy(match::checkAlreadyConfirmed);
    }

    // TODO 매치를 확정 하는 로직
    void confirm_the_match_post() {
        // given
        Team host = testDataBuilder.createDefaultTeam(1L, 1L);
        Team participant = testDataBuilder.createDefaultTeam(2L, 2L);
        Match match = testDataBuilder.createDefaultMatch(1L, host);
        // when
        match.confirmMatch(participant);
        // then
        Assertions.assertThatExceptionOfType(MatchAlreadyConfirmedException.class)
                .isThrownBy(match::checkAlreadyConfirmed);
    }




    // TODO 매치가 


}
