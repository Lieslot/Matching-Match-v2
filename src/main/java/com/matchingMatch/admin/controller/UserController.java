package com.matchingMatch.admin.controller;


import com.matchingMatch.auth.AuthenticatedAdmin;
import com.matchingMatch.auth.Authentication;
import com.matchingMatch.auth.dto.UserAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserController {

    @PutMapping("/ban")
    @AuthenticatedAdmin
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void banUser(@Authentication UserAuth userAuth) {

    }

    @PutMapping("/unban")
    @AuthenticatedAdmin
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void unbanUser(@Authentication UserAuth userAuth) {

    }


    @GetMapping("/users")
    @AuthenticatedAdmin
    @ResponseStatus(HttpStatus.OK)
    public void searchUsers(@Authentication UserAuth userAuth) {

    }

    @DeleteMapping("/user")
    @AuthenticatedAdmin
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void withdrawUser(@Authentication UserAuth userAuth) {

    }


}
