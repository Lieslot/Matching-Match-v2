package com.matchingMatch.notification.domain;

import com.matchingMatch.match.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmSubscription extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String fcmToken;


    @Builder
    public FcmSubscription(Long id, Long userId, String fcmToken) {
        this.id = id;
        this.userId = userId;
        this.fcmToken = fcmToken;
    }
}
