package com.matchingMatch.auth.domain;


import com.matchingMatch.match.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends BaseEntity {

    public RefreshToken(Long teamId, String content) {
        this.teamId = teamId;
        this.content = content;
    }

    @Id
    private Long teamId;


    private String content;

}
