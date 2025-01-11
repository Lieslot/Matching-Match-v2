package com.matchingMatch.chat.service;

import com.matchingMatch.chat.dto.BlockedUserResponse;
import com.matchingMatch.chat.dto.BlockedUsersResponse;
import com.matchingMatch.chat.entity.BlockChatUserEntity;
import com.matchingMatch.chat.entity.repository.BlockUserRepository;
import com.matchingMatch.chat.entity.repository.ChatRoomParticipantRepository;
import com.matchingMatch.chat.entity.repository.ChatRoomRepository;
import com.matchingMatch.match.TeamAdapter;
import com.matchingMatch.user.domain.UserDetail;
import com.matchingMatch.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatUserService {


    private final UserRepository userRepository;
    private final BlockUserRepository blockUserRepository;
    private final TeamAdapter teamAdapter;

    @Transactional
    public void blockUser(Long userId, Long blockTeamId) {

        Long blockUserId = teamAdapter.getTeamBy(blockTeamId).getLeaderId();

        boolean isBlockUserExists = userRepository.existsById(blockUserId);
        if (!isBlockUserExists) {
            throw new IllegalArgumentException("차단할 유저가 존재하지 않습니다.");
        }

        Optional<BlockChatUserEntity> result = blockUserRepository.findByUserIdAndBlockUserId(userId, blockUserId);

        if (result.isEmpty()) {
            blockUserRepository.save(BlockChatUserEntity.builder()
                    .userId(userId)
                    .blockTeamId(blockTeamId)
                    .blockUserId(blockUserId)
                    .build());
        }

    }

    @Transactional
    public void unblockUser(Long userId, Long blockTeamId) {

        Long blockUserId = teamAdapter.getTeamBy(blockTeamId).getLeaderId();

        boolean isBlockUserExists = userRepository.existsById(blockUserId);
        if (!isBlockUserExists) {
            throw new IllegalArgumentException("차단된 유저가 존재하지 않습니다.");
        }

        BlockChatUserEntity blockChatUser = blockUserRepository.findByUserIdAndBlockUserId(userId, blockUserId).orElseThrow(
                () -> new IllegalArgumentException("잘못된 접근입니다.")
        );

        blockUserRepository.delete(blockChatUser);
    }

    public BlockedUsersResponse getBlockUsers(Long userId) {
        List<BlockChatUserEntity> blockedUsers = blockUserRepository.findAllByUserId(userId);
        // TODO dto 변환하기
        List<UserDetail> userDetails = userRepository.findAllById(blockedUsers.stream().map(BlockChatUserEntity::getBlockUserId).toList());

        List<BlockedUserResponse> blockedUserResponses = userDetails.stream()
                .map(userDetail -> BlockedUserResponse.builder()
                        .id(userDetail.getId())
                        .nickname(userDetail.getNickname())
                        .build())
                .toList();

        return new BlockedUsersResponse(blockedUserResponses);
    }


}
