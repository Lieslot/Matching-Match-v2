package com.matchingMatch.user.domain;

import com.matchingMatch.match.domain.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private Role role;

    // TODO: 기본값 정하기
    private LocalDate banDeadLine;

    @Builder
    public UserDetail(Long id, String password, String username, Role role, LocalDate banDeadLine, String nickname) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.role = role;
        this.banDeadLine = banDeadLine;
        this.nickname = nickname;
    }


}
