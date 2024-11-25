package com.matchingMatch.match;


import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.service.MatchService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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

    TestDataBuilder testDataBuilder = new TestDataBuilder();

    @Captor
    private ArgumentCaptor<Match> matchArgumentCaptor;



}
