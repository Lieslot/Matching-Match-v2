package com.matchingMatch.match.domain.entity;

import com.matchingMatch.match.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MannerRateCheckEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long matchId;

    private Long teaId;

    private Boolean isCheck;

    public void check() {
        isCheck = true;
    }

    @Builder
    public MannerRateCheckEntity(Long matchId, Long teaId) {
        this.matchId = matchId;
        this.teaId = teaId;
        this.isCheck = false;
    }

}
