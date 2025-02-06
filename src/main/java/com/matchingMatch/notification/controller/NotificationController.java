package com.matchingMatch.notification.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.matchingMatch.auth.AuthenticatedUser;
import com.matchingMatch.auth.Authentication;
import com.matchingMatch.auth.dto.UserAuth;
import com.matchingMatch.notification.dto.PublicSubscriptionRequest;
import com.matchingMatch.notification.service.NotificationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

	private final NotificationService pushService;

	@PostMapping("/subscribe")
	@ResponseStatus(HttpStatus.OK)
	@AuthenticatedUser
	public void subscribe(@Valid @RequestBody PublicSubscriptionRequest publicSubscriptionRequest,
		@Authentication UserAuth userAuth) {

		pushService.subscribe(publicSubscriptionRequest, userAuth.getId());

	}

}
