package com.matchingMatch.notification.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.security.Key;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PublicSubscriptionRequest {

    private String endpoint;
    private Key keys;

    @Getter
    public static class Key {
        private String p256dh;
        private String auth;

        public Key(String p256dh, String auth) {
            this.p256dh = p256dh;
            this.auth = auth;
        }
    }

}
