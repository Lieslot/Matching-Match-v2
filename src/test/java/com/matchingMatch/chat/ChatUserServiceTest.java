package com.matchingMatch.chat;

import com.matchingMatch.chat.entity.BlockChatUserEntity;
import com.matchingMatch.chat.entity.repository.BlockUserRepository;
import com.matchingMatch.chat.service.ChatUserService;
import com.matchingMatch.match.implement.TeamAdapter;
import com.matchingMatch.match.domain.enums.Role;
import com.matchingMatch.user.domain.UserDetail;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import com.matchingMatch.TestDataBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({ChatUserService.class, TeamAdapter.class})
public class ChatUserServiceTest {

    private static final String PASSWORD = "password1234";
    private static final String ACCOUNT = "rlaxodud";

    @Autowired
    private ChatUserService chatUserService;

    TestDataBuilder testDataBuilder = new TestDataBuilder();

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BlockUserRepository blockUserRepository;

    UserDetail user;
    UserDetail target;
    @Autowired
    private TeamAdapter teamAdapter;

    @BeforeEach
    void setUp() {
        user = UserDetail.builder()
                .username(ACCOUNT)
                .password(PASSWORD)
                .nickname("default")
                .role(Role.USER)
                .banDeadLine(LocalDate.MIN)
                .build();
        target = UserDetail.builder()
                .username("rlaxodud2")
                .password(PASSWORD)
                .nickname("default2")
                .role(Role.USER)
                .banDeadLine(LocalDate.MIN)
                .build();


        testEntityManager.persist(user);
        testEntityManager.persist(target);
    }

    @AfterEach
    void tearDown() {
            testEntityManager.getEntityManager().createNativeQuery("TRUNCATE TABLE user_detail").executeUpdate();
            testEntityManager.getEntityManager().createNativeQuery("TRUNCATE TABLE match").executeUpdate();
            testEntityManager.getEntityManager().createNativeQuery("TRUNCATE TABLE team").executeUpdate();
    }

    // 차단 성공 테스트

    @Test
    public void blockParticipantSuccess() {
        // given
        Long userId = user.getId();
        Long blockUserId = target.getId();
        teamAdapter.save(testDataBuilder.createNotPersistedTeam(userId, "default"));
        Long blockTeamId = teamAdapter.save(testDataBuilder.createNotPersistedTeam(blockUserId, "default2"));


        // when
        chatUserService.blockUser(userId, blockTeamId);

        // then
        BlockChatUserEntity blockChatUser = blockUserRepository.findByUserIdAndBlockUserId(userId, blockUserId).orElseThrow(
                () -> new IllegalArgumentException("차단 실패")
        );

        assertThat(blockChatUser.getUserId()).isEqualTo(userId);
        assertThat(blockChatUser.getBlockUserId()).isEqualTo(blockUserId);
        // 차단 성공
    }

    // 차단 실패 테스트

    // 차단 해제 성공 테스트
    @Test
    public void unblockParticipantSuccess() {
        // given
        Long userId = user.getId();
        Long blockUserId = target.getId();
        Long blockTeamId = teamAdapter.save(testDataBuilder.createNotPersistedTeam(blockUserId, "default2"));

        chatUserService.blockUser(userId, blockTeamId);

        // when
        chatUserService.unblockUser(userId, blockTeamId);

        // then
        blockUserRepository.deleteByUserIdAndBlockUserId(userId, blockTeamId);

        assertThat(blockUserRepository.findAllByUserId(userId)).isEmpty();
        // 차단 성공
    }



    // 차단 해제 실패 테스트
}
