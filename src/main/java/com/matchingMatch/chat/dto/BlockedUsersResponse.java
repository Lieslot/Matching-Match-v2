package com.matchingMatch.chat.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BlockedUsersResponse {
    List<BlockedUserResponse> blockedUserResponses;


    public BlockedUsersResponse(List<BlockedUserResponse> blockedUserResponses) {
        this.blockedUserResponses = blockedUserResponses;
    }
}
