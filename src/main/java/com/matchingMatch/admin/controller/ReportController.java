package com.matchingMatch.admin.controller;


import com.matchingMatch.admin.Report;
import com.matchingMatch.admin.dto.ReportRequest;
import com.matchingMatch.admin.service.ReportService;
import com.matchingMatch.auth.AuthenticatedAdmin;
import com.matchingMatch.auth.AuthenticatedUser;
import java.net.URI;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    @AuthenticatedUser
    public ResponseEntity<Void> createReport(@RequestBody ReportRequest reportRequest) {

        reportService.createReport(reportRequest);

        return ResponseEntity.ok().build();

    }

    // TODO 추후에 필요한 것만 반환하도록 DTO 만들어서 최적화하기
    @GetMapping
    @AuthenticatedAdmin
    public Slice<Report> getReports(
            @RequestParam(value = "lastId", defaultValue = "", required = false) Long lastId) {
        return reportService.getReports(lastId);
    }

}
