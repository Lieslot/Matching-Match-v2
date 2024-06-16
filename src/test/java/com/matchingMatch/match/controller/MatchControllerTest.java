package com.matchingMatch.match.controller;


import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingMatch.match.domain.Match;
import com.matchingMatch.team.domain.Team;
import com.matchingMatch.match.domain.enums.Gender;
import com.matchingMatch.match.domain.enums.Role;
import com.matchingMatch.match.dto.MatchPostRequest;
import com.matchingMatch.match.service.MatchService;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(MatchPostController.class)
public class MatchControllerTest {

    @MockBean
    private MatchService matchService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Team team;

    @BeforeEach
    void setUp() {
        team = Team.builder()
                   .teamName("ss")
                   .gender(Gender.FEMALE)
                   .teamDescription("ddd")
                   .account("dddd")
                   .password("dddd")
                   .role(Role.USER)
                   .region("서울 관악구")
                   .build();

    }

    @Test
    @Transactional
    void 매치를_등록하면_매치_포스트_페이지로_리다이렉트된다() throws Exception {
        // TODO Controller 분리 테스트 방법 생각해보기

        MatchPostRequest matchPostRequest = MatchPostRequest.builder()
                                                            .hostId(team)
                                                            .startTime(LocalDateTime.of(2024, Month.MAY, 15, 20, 15))
                                                            .endTime(LocalDateTime.of(2024, Month.MAY, 15, 20, 55))
                                                            .stadiumCost(15000)
                                                            .gender(Gender.FEMALE)
                                                            .build();

        String matchPostRequestJson = objectMapper.writeValueAsString(matchPostRequest);
        when(matchService.postNewMatch(any(Match.class), any(Long.class))).thenReturn(1L);

        mockMvc.perform(post("/match/post/create")
                       .with(csrf()) // post이므로 csrf 추가 안하면 403 반환
                       .content(matchPostRequestJson)
                       .contentType(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpectAll(status().isCreated(),
                       header().string(LOCATION, "/match/post/1") // redirect 요소 테스트
               );

        verify(matchService).postNewMatch(any(Match.class), any(Long.class));

    }


}
