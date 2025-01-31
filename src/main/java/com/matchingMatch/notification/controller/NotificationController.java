package com.matchingMatch.notification.controller;


import com.matchingMatch.auth.AuthenticatedUser;
import com.matchingMatch.auth.Authentication;
import com.matchingMatch.notification.domain.PublicSubscription;
import com.matchingMatch.notification.dto.PublicSubscriptionRequest;
import com.matchingMatch.notification.service.PushService;
import com.matchingMatch.team.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    private final PushService pushService;


    @PostMapping("/subscribe")
    @ResponseStatus(HttpStatus.OK)
    @AuthenticatedUser
    public void subscribe(@Valid @RequestBody PublicSubscriptionRequest publicSubscriptionRequest, @Authentication Long userId) {

        pushService.subscribe(publicSubscriptionRequest, userId);

    }




}
