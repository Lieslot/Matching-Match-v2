package com.matchingMatch.match.domain;

import com.matchingMatch.match.TeamAdapter;
import com.matchingMatch.team.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@DataJpaTest
@SpringBootTest(classes = {TeamService.class, TeamAdapter.class})
public class TeamServiceTest {

    @Autowired
    private TeamService teamService;
    @Autowired
    private TeamAdapter teamAdapter;

}
