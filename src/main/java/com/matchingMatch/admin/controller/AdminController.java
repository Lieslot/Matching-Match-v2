package com.matchingMatch.admin.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.matchingMatch.admin.dto.UserBanRequest;
import com.matchingMatch.admin.dto.UserListElement;
import com.matchingMatch.admin.dto.UserListResponse;
import com.matchingMatch.admin.dto.UserUnbanRequest;
import com.matchingMatch.admin.dto.UserWithDrawRequest;
import com.matchingMatch.admin.service.AdminService;
import com.matchingMatch.auth.AuthenticatedAdmin;
import com.matchingMatch.auth.Authentication;
import com.matchingMatch.auth.dto.UserAuth;
import com.matchingMatch.user.domain.UserDetail;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

	private final AdminService adminService;

	@PutMapping("/ban")
	@AuthenticatedAdmin
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void banUser(@Valid @RequestBody UserBanRequest userBanRequest, @Authentication UserAuth userAuth) {

		adminService.banUser(userBanRequest.getId(), userBanRequest.getDay());
	}

	@PutMapping("/unban")
	@AuthenticatedAdmin
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void unbanUser(@Valid @RequestBody UserUnbanRequest userUnbanRequest, @Authentication UserAuth userAuth) {

		adminService.unBanUser(userUnbanRequest.getId());
	}

	@GetMapping("/users")
	@AuthenticatedAdmin
	@ResponseStatus(HttpStatus.OK)
	public UserListResponse searchUsers(@RequestParam(defaultValue = "0") int page, @Authentication UserAuth userAuth) {

		List<UserDetail> users = adminService.getUsers(page, 50);
		List<UserListElement> userListElements = users.stream()
			.map(user -> UserListElement.builder()
				.id(user.getId())
				.username(user.getUsername())
				.nickname(user.getNickname())
				.banDeadLine(user.getBanDeadLine())
				.build()).toList();
		return new UserListResponse(userListElements);

	}

	@DeleteMapping("/user")
	@AuthenticatedAdmin
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void withdrawUser(@Valid @RequestBody UserWithDrawRequest userWithDrawRequest,
		@Authentication UserAuth userAuth) {
		adminService.withDrawUser(userWithDrawRequest.getId());
	}

}
