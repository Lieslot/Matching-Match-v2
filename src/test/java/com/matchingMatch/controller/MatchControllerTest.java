package com.matchingMatch.controller;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.matchingMatch.match.controller.MatchController;
import com.matchingMatch.match.domain.Team;
import com.matchingMatch.match.domain.enums.Gender;
import com.matchingMatch.match.domain.repository.TeamRepository;
import com.matchingMatch.match.dto.MatchPostRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
public class MatchControllerTest {

    @Autowired
    private MatchController matchController;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    void 매치를_등록하면_매치_포스트_페이지로_리다이렉트된다() throws Exception {
//        Team team = Team.builder()
//                .teamName("ss")
//                .gender(Gender.FEMALE)
//                .teamDescription("ddd")
//                .account("dddd")
//                .password("dddd")
//                .;
//
//        MatchPostRequest.builder()
//                                .hostId()
//
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/match/create")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpectAll(
//                        status().isCreated(),
//                        redirectedUrl("match/post/{id}", ),
//                        jsonPath("$.hostId").exists(),
//                        jsonPath("$.participantId").exists()
//                )
//
//    }






}
