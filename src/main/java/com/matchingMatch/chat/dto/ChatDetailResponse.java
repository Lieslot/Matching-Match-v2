package com.matchingMatch.chat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ChatDetailResponse {

    @NotNull
    private List<ChatDetail> chatDetails;
    @NotNull
    private Long roomId;
    @NotNull
    private String myTeamName;
    @NotNull
    private String myTeamLogoUrl;
    @NotNull
    private String targetTeamName;
    @NotNull
    private String targetTeamLogoUrl;

    @Builder
    public ChatDetailResponse(List<ChatDetail> chatDetails, Long roomId, String myTeamName, String myTeamLogoUrl, String targetTeamName, String targetTeamLogoUrl) {
        this.chatDetails = chatDetails;
        this.roomId = roomId;
        this.myTeamName = myTeamName;
        this.myTeamLogoUrl = myTeamLogoUrl;
        this.targetTeamName = targetTeamName;
        this.targetTeamLogoUrl = targetTeamLogoUrl;
    }

}
