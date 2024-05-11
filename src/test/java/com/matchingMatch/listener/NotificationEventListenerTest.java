package com.matchingMatch.listener;


import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.Team;
import com.matchingMatch.match.domain.enums.Gender;
import com.matchingMatch.match.domain.enums.Role;
import com.matchingMatch.match.domain.repository.MatchRepository;
import com.matchingMatch.match.domain.repository.TeamRepository;
import com.matchingMatch.match.dto.MatchCancelEvent;
import com.matchingMatch.match.dto.MatchConfirmEvent;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;



// TODO 비동기 이벤트에서도 한 번에 모든 테스트가 성공하도록 만들어보기

//@SpringBootTest
@DataJpaTest
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestExecutionListeners(value = {AcceptanceTestExecutionListener.class}, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
public class NotificationEventListenerTest {

    private static final Logger log = LoggerFactory.getLogger(NotificationEventListenerTest.class);
    Match match;
    Team hostTeam;
    Team otherTeam;
    @SpyBean
    private NotificationEventListener testNotificationEventListener;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @BeforeEach
    @Transactional
    void setUp() {
        hostTeam = Team.builder()
                       .teamName("ss")
                       .gender(Gender.FEMALE)
                       .teamDescription("ddd")
                       .account("dddd")
                       .password("dddd")
                       .role(Role.USER)
                       .region("서울 관악구")
                       .build();
        otherTeam = Team.builder()
                        .teamName("sdw")
                        .gender(Gender.FEMALE)
                        .teamDescription("ddd")
                        .account("sdw")
                        .password("dddd")
                        .role(Role.USER)
                        .region("서울 관악구")
                        .build();

        teamRepository.save(hostTeam);
        teamRepository.save(otherTeam);
        match = Match.builder()
                     .host(hostTeam)
                     .startTime(
                             LocalDateTime.now())
                     .endTime(LocalDateTime.now()
                                           .plusSeconds(1))
                     .stadiumCost(15000)
                     .gender(Gender.FEMALE)
                     .build();

        matchRepository.save(match);

    }

    @AfterEach
    void erase() {
        // 참조 무결성 제약으로 인해 match를 먼저 삭제해야 정상적으로 동작함.
        matchRepository.deleteAll();
        teamRepository.deleteAll();

    }




    @Test
    @Order(2)
    void 매치_확정이_롤백되면_상대에게_알람이_발급되지_않는지_테스트() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        CountDownLatch countDownLatch = new CountDownLatch(1);

        executorService.execute(() -> {

            TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

            transactionTemplate.execute(status -> {
                MatchConfirmEvent matchConfirmEvent = new MatchConfirmEvent(otherTeam, match);
                log.info("발급");
                applicationEventPublisher.publishEvent(matchConfirmEvent);

                status.setRollbackOnly();

                return null;
            });
            countDownLatch.countDown();
        });

        countDownLatch.await();

        Assertions.assertThat(otherTeam.getNotifications())
                  .isEmpty();
    }


    @Test
    @Order(1)
    void 매치_확정_이후_상대에게_알람이_가는지_테스트() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        CountDownLatch countDownLatch = new CountDownLatch(1);

        executorService.execute(() -> {

            TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);



            transactionTemplate.execute(status -> {

                MatchConfirmEvent matchConfirmEvent = new MatchConfirmEvent(otherTeam, match);

                log.info("발급");
                applicationEventPublisher.publishEvent(matchConfirmEvent);

                return null;
            });
            countDownLatch.countDown();
        });

        countDownLatch.await();


        Assertions.assertThat(otherTeam.getNotifications())
                  .isNotEmpty();

        verify(testNotificationEventListener).publishMatchConfirmNotification(any(MatchConfirmEvent.class));

    }

    @Test
    @Order(3)
    void 매치_확정_이후_취소했을_때_상대에게_알람이_가는지_확인() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        CountDownLatch countDownLatch = new CountDownLatch(1);

        executorService.execute(() -> {

            TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

            transactionTemplate.execute(status -> {
                MatchCancelEvent matchCancelEvent = new MatchCancelEvent(otherTeam, match);

                log.info("발급");
                applicationEventPublisher.publishEvent(matchCancelEvent);

                return null;
            });
            countDownLatch.countDown();
        });

        countDownLatch.await();


        Assertions.assertThat(otherTeam.getNotifications())
                  .isNotEmpty();


    }



}
