package com.matchingMatch.notification.common;

import com.matchingMatch.notification.domain.MatchNotificationType;

import java.util.Arrays;

public enum NotificationTemplate {
    MANNER_RATE("매너 평가 알림", "종료된 매치에 대해 매너평가를 남겨주세요."),
    MATCH_CONFIRM("매치 확정 알림", "{0} 팀과의 매치가 확정되었습니다."),
    MATCH_CANCEL("매치 취소 알림", "{0} 팀과의 매치가 취소되었습니다."),
    MATCH_REQUEST("매치 요청 알림", "{0} 팀의 매치 요청이 도착했습니다."),
    NOTICE("공지사항 알림", "새로운 공지사항이 도착했습니다.");

    private final String title;
    private final String messageTemplate;

    NotificationTemplate(String title, String messageTemplate) {
        this.title = title;
        this.messageTemplate = messageTemplate;
    }

    public static NotificationTemplate findTemplateByType(MatchNotificationType notificationType) {
        return Arrays.stream(NotificationTemplate.values())
                .filter(template -> template.name().equals(notificationType.name()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 템플릿이 없습니다."));
    }

    public String getTitle() {
        return title;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }
}

