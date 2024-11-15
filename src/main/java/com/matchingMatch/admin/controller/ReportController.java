package com.matchingMatch.admin.controller;


import com.matchingMatch.admin.dto.GetReportResponse;
import com.matchingMatch.admin.dto.ModifyReportReplyRequest;
import com.matchingMatch.admin.dto.ModifyReportReplyResponse;
import com.matchingMatch.admin.dto.ModifyReportRequest;
import com.matchingMatch.admin.dto.ModifyReportResponse;
import com.matchingMatch.admin.dto.PostReportReplyRequest;
import com.matchingMatch.admin.dto.PostReportReplyResponse;
import com.matchingMatch.admin.dto.PostReportRequest;
import com.matchingMatch.admin.dto.PostReportResponse;
import com.matchingMatch.admin.service.ReportService;
import com.matchingMatch.auth.AuthenticatedAdmin;
import com.matchingMatch.auth.AuthenticatedUser;
import com.matchingMatch.auth.Authentication;
import com.matchingMatch.auth.dto.UserAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    @AuthenticatedUser
    @ResponseStatus(HttpStatus.CREATED)
    public PostReportResponse createReport(@RequestBody PostReportRequest reportRequest) {

    }

    // TODO 작성한 문의 RUD 추가


    @GetMapping("/{reportId}")
    @AuthenticatedUser
    @ResponseStatus(HttpStatus.OK)
    public GetReportResponse getReport(@RequestParam Long reportId) {



    }



    @GetMapping("/user")
    @AuthenticatedUser
    @ResponseStatus(HttpStatus.OK)
    public List<GetReportResponse> getUserReports(@Authentication UserAuth userAuth) {



    }

    @GetMapping("/admin")
    @AuthenticatedAdmin
    @ResponseStatus(HttpStatus.OK)
    public List<GetReportResponse> getAdminReports(@Authentication UserAuth userAuth) {

    }

    @DeleteMapping("/{reportId}")
    @AuthenticatedUser
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReport(@RequestParam Long reportId) {

    }



    @PutMapping("/{reportId}")
    @AuthenticatedUser
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ModifyReportResponse updateReport(@RequestParam Long reportId,
                                             @RequestBody ModifyReportRequest reportRequest) {

    }


    @PostMapping("/{reportId}")
    @AuthenticatedAdmin
    @ResponseStatus(HttpStatus.CREATED)
    public PostReportReplyResponse replyReport(@RequestParam Long reportId,
                                               @RequestBody PostReportReplyRequest reportRequest) {


    }



    @PutMapping("/{reportId}")
    @AuthenticatedAdmin
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ModifyReportReplyResponse updateReportReply(@RequestParam Long reportId,
                                                       @RequestBody ModifyReportReplyRequest reportRequest) {

    }

    @DeleteMapping("/{reportId}")
    @AuthenticatedUser
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReportReply(@RequestParam Long reportId) {

    }



}
