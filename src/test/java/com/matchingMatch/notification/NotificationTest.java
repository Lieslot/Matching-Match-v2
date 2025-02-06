package com.matchingMatch.notification;

import com.matchingMatch.TestDataBuilder;
import com.matchingMatch.match.domain.StadiumAdapter;
import com.matchingMatch.match.domain.entity.MatchEntity;
import com.matchingMatch.notification.implement.FcmMatchNotificationPusher;
import com.matchingMatch.notification.domain.MatchNotificationRepository;
import com.matchingMatch.match.implement.MannerRater;
import com.matchingMatch.match.implement.MatchAdapter;
import com.matchingMatch.match.implement.MatchRequestAdapter;
import com.matchingMatch.match.implement.MatchTeamValidator;
import com.matchingMatch.match.implement.TeamAdapter;
import com.matchingMatch.match.service.MatchingService;
import com.matchingMatch.team.domain.entity.Team;
import com.matchingMatch.user.domain.UserDetail;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DataJpaTest

@Import({MatchingService.class, MatchAdapter.class, MatchRequestAdapter.class, TeamAdapter.class
        , MatchTeamValidator.class, MannerRater.class, FcmMatchNotificationPusher.class, StadiumAdapter.class})
public class NotificationTest {


    @Autowired
    private MatchingService matchingService;
    @Autowired
    private MatchAdapter matchAdapter;
    @Autowired
    private MatchRequestAdapter matchRequestAdapter;
    @Autowired
    private TeamAdapter teamAdapter;
    @Autowired
    private MatchTeamValidator matchTeamValidator;
    @Autowired
    private MannerRater mannerRater;
    @Autowired
    private MatchNotificationRepository notificationRepository;

    private TestDataBuilder testDataBuilder = new TestDataBuilder();
    @Autowired
    private TestEntityManager testEntityManager;

    private UserDetail hostUser;
    private UserDetail guestUser;
    private Team hostTeam;
    private Team guestTeam;
    private Long hostId;
    private Long guestId;
    private MatchEntity match;

    @Autowired
    private FcmMatchNotificationPusher fcmMatchNotificationPusher;


    @BeforeEach
    void setUp() {
        hostUser = testDataBuilder.createNotPersistedUser("host");
        guestUser = testDataBuilder.createNotPersistedUser("guest");
        testEntityManager.persist(hostUser);
        testEntityManager.persist(guestUser);

        hostTeam = testDataBuilder.createNotPersistedTeam(hostUser.getId(), "default");
        guestTeam = testDataBuilder.createNotPersistedTeam(guestUser.getId(), "default1");
        hostId = teamAdapter.save(hostTeam);
        guestId = teamAdapter.save(guestTeam);

        match = testDataBuilder.createNotPersistedMatchEntity(hostId);
        matchAdapter.save(match);
    }



}
