package com.matchingMatch.controller;


import static org.mockito.Mockito.*;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingMatch.match.controller.MatchController;
import com.matchingMatch.match.controller.TeamController;
import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.Team;
import com.matchingMatch.match.domain.enums.Gender;
import com.matchingMatch.match.domain.enums.Role;
import com.matchingMatch.match.domain.repository.MatchRepository;
import com.matchingMatch.match.domain.repository.TeamRepository;
import com.matchingMatch.match.dto.MatchPostRequest;
import com.matchingMatch.match.service.MatchService;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(MatchController.class)
public class MatchControllerTest {

    @MockBean
    private MatchService matchService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser
    @Test
    void 매치를_등록하면_매치_포스트_페이지로_리다이렉트된다() throws Exception {
        // WithMockUser를 이용해서 Team을 대체할 방법은 없나?
        Team team = Team.builder()
                .teamName("ss")
                .gender(Gender.FEMALE)
                .teamDescription("ddd")
                .account("dddd")
                .password("dddd")
                .role(Role.USER)
                .region("서울 관악구")
                .build();
        // 매치를 요청
        MatchPostRequest matchPostRequest = MatchPostRequest.builder()
                .hostId(team)
                .startTime(LocalDateTime.of(2024, Month.MAY, 15, 20, 15))
                .endTime(LocalDateTime.of(2024, Month.MAY, 15, 20, 15))
                .stadiumCost(15000)
                .gender(Gender.FEMALE)
                .build();
        Match entity = matchPostRequest.toEntity();

        String matchPostRequestJson = objectMapper.writeValueAsString(matchPostRequest);
        when(matchService.save(any(Match.class))).thenReturn(1L);

        mockMvc.perform(post("/match/create")
                        .with(csrf()) // post이므로 csrf 추가 안하면 403 반환
                        .content(matchPostRequestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(status().isCreated(),
                        header().string(LOCATION, "/match/post/1") // redirect 요소 테스트
                        );


        verify(matchService).save(any(Match.class));

    }


}
