package com.matchingMatch.admin.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UserListResponse {

    private List<UserListElement> userList;

    public UserListResponse(List<UserListElement> userList) {
        this.userList = userList;
    }




}
