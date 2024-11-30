package com.matchingMatch.chat.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ChatDetailResponse {

    private List<ChatDetail> chatDetails;
    private Long roomId;
    private String myTeamName;
    private String myTeamLogoUrl;
    private String targetTeamName;
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
