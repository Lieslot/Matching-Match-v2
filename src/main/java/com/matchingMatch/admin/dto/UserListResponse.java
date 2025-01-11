package com.matchingMatch.admin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UserListResponse {

    @NotNull
    private List<UserListElement> userList;

    public UserListResponse(List<UserListElement> userList) {
        this.userList = userList;
    }




}
