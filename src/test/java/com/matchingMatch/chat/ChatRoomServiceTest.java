package com.matchingMatch.chat;


import com.matchingMatch.chat.entity.BlockChatUserEntity;
import com.matchingMatch.chat.entity.repository.BlockUserRepository;
import com.matchingMatch.chat.service.ChatRoomService;
import com.matchingMatch.match.domain.enums.Role;
import com.matchingMatch.user.domain.UserDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(ChatRoomService.class)
public class ChatRoomServiceTest {




}
