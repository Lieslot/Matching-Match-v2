package com.matchingMatch.notification;

import com.matchingMatch.TestDataBuilder;
import com.matchingMatch.match.domain.entity.MatchEntity;
import com.matchingMatch.notification.implement.FcmMatchNotificationPusher;
import com.matchingMatch.notification.domain.MatchNotificationEntity;
import com.matchingMatch.notification.domain.MatchNotificationRepository;
import com.matchingMatch.match.MannerRater;
import com.matchingMatch.match.MatchAdapter;
import com.matchingMatch.match.MatchRequestAdapter;
import com.matchingMatch.match.MatchTeamValidator;
import com.matchingMatch.match.TeamAdapter;
import com.matchingMatch.match.service.MatchingService;
import com.matchingMatch.notification.domain.entity.MatchNotificationType;
import com.matchingMatch.team.domain.entity.Team;
import com.matchingMatch.user.domain.UserDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DataJpaTest

@Import({MatchingService.class, MatchAdapter.class, MatchRequestAdapter.class, TeamAdapter.class
        , MatchTeamValidator.class, MannerRater.class, FcmMatchNotificationPusher.class})
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

    @Test
    void test() {
        fcmMatchNotificationPusher.push(MatchNotificationEntity.builder()
                .targetMatchId(match.getId())
                .targetTeamId(guestId)
                .sendTeamId(hostId)
                .notificationType(MatchNotificationType.MATCH_REQUEST)
                .build());
    }


}
