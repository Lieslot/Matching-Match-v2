package com.matchingMatch.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@AllArgsConstructor
public class GetChatRoomPreviewResponse {

    List<ChatRoomPreview> previews;
}
