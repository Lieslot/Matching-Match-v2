package com.matchingMatch.match;


import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.exception.MatchAlreadyConfirmedException;
import com.matchingMatch.match.service.MatchRequestAdapter;
import com.matchingMatch.match.service.MatchService;
import com.matchingMatch.team.domain.entity.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {MatchService.class})
public class MatchingServiceTest {


    @Autowired
    private MatchService matchService;

    @MockBean
    private MatchAdapter matchAdapter;

    @MockBean
    private MatchRequestAdapter matchRequestAdapter;

    @MockBean
    private TeamAdapter teamAdapter;

    private TestDataBuilder testDataBuilder = new TestDataBuilder();

    @Captor
    private ArgumentCaptor<Match> matchArgumentCaptor;


    // 매치가 이미 확정된 경우 예외처리

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


}
