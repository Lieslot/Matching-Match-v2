package com.matchingMatch.chat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class BlockedUsersResponse {

    @NotNull
    List<BlockedUserResponse> blockedUserResponses;


    public BlockedUsersResponse(List<BlockedUserResponse> blockedUserResponses) {
        this.blockedUserResponses = blockedUserResponses;
    }
}
