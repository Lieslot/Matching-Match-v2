package com.matchingMatch.admin.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class UserListElement {
    private Long id;
    private String username;
    private String nickname;
    private LocalDate banDeadLine;

    @Builder
    public UserListElement(Long id, String username, String nickname, LocalDate banDeadLine) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.banDeadLine = banDeadLine;
    }

}
