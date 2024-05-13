package com.matchingMatch.match.service;


import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.Team;
import com.matchingMatch.match.domain.enums.Gender;
import com.matchingMatch.match.domain.enums.Role;
import com.matchingMatch.match.domain.repository.TeamRepository;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

// 테스트에 필요한 파트
// 1. JPA를 통해 엔티티가 생성되는 것 -> entityScan으로 해결 가능할까??
// 2. Spring Data JPA에서 JpaRepository 의존성 주입하는 거 -> @DataJpaTest
// 3. 스프링 빈 설정 -> @SpringBootTest classes 값을 설정해서 필요한 것만 가져오기 OR @Import 이용

@SpringBootTest
public class MatchServiceTest {


    @Autowired
    private MatchService matchService;

    @Autowired
    private TeamRepository teamRepository;

    private Team team;
    Match match;
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

        teamRepository.save(team);
        match = Match.builder()
                           .host(team)
                           .startTime(
                                   LocalDateTime.of(2024, Month.MAY, 15, 20, 15))
                           .endTime(LocalDateTime.of(2024, Month.MAY, 15, 20, 55))
                           .stadiumCost(15000)
                           .gender(Gender.FEMALE)
                           .build();
    }

    @AfterEach
    void clear() {

        teamRepository.deleteAll();
    }



    @Transactional
    @Test
    void 매치의_호스트와_삭제_요청을_보낸_유저가_다르면_예외가_발생한다() {
        Long anotherUserId = 0L;

        matchService.save(match, team.getId());

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> matchService.deleteMatchPostBy(match.getId(), anotherUserId));

    }

    @Transactional
    @Test
    void 매치의_호스트와_수정_요청을_보낸_유저가_다르면_예외가_발생한다() {

        Long anotherUserId = 0L;

        matchService.save(match, team.getId());

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> matchService.updateMatch(match, anotherUserId));
    }




}
