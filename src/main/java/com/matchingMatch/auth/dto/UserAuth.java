package com.matchingMatch.auth.dto;


import com.matchingMatch.match.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuth {

    private Long id;
    private Role role;

    public static UserAuth team(Long id) {

        return new UserAuth(id, Role.USER);
    }




}
