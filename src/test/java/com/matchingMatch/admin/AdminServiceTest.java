package com.matchingMatch.admin;


import com.matchingMatch.admin.service.AdminService;
import com.matchingMatch.match.domain.enums.Role;
import com.matchingMatch.user.domain.UserDetail;
import com.matchingMatch.user.domain.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

@DataJpaTest
@Import(AdminService.class)
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private TestEntityManager testEntityManager;

    private UserDetail userDetail;

    @BeforeEach
    void setUp() {
        userDetail = UserDetail.builder()
                .username("test")
                .password("test")
                .nickname("test")
                .role(Role.USER)
                .build();
        userDetail = testEntityManager.persist(userDetail);
    }

    @AfterEach
    void tearDown() {
        testEntityManager.getEntityManager().createNativeQuery("TRUNCATE TABLE user_detail").executeUpdate();
    }



    // 밴 테스트
    @Test
    void banUserSuccess() {
        // given
        // when
        adminService.banUser(userDetail.getId(), 7);
        // then
        UserDetail newUserDetail = testEntityManager.find(UserDetail.class, userDetail.getId());
        LocalDate now = LocalDate.now();
        LocalDate banDeadLine = now.plusDays(7);

        Assertions.assertThat(newUserDetail.getBanDeadLine()).isEqualTo(banDeadLine);


    }

    // 언밴 테스트

    @Test
    void unban_user_test() {
        // given
        // when
        adminService.unBanUser(userDetail.getId());
        // then
        UserDetail newUserDetail = testEntityManager.find(UserDetail.class, userDetail.getId());

        Assertions.assertThat(newUserDetail.isBanned()).isFalse();

    }
    // 유저 탈퇴 테스트

    @Test
    void withdraw_user_test() {
        // given
        // when
        adminService.withDrawUser(userDetail.getId());
        // then
        UserDetail newUserDetail = testEntityManager.find(UserDetail.class, userDetail.getId());

        Assertions.assertThat(newUserDetail).isNull();

    }


}
