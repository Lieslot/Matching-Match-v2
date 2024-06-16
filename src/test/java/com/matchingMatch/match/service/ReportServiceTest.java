package com.matchingMatch.match.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.matchingMatch.admin.Report;
import com.matchingMatch.admin.domain.ReportRepository;
import com.matchingMatch.admin.dto.ReportRequest;
import com.matchingMatch.admin.service.ReportService;
import com.matchingMatch.team.domain.Team;
import com.matchingMatch.match.domain.enums.Gender;
import com.matchingMatch.match.domain.enums.Role;
import com.matchingMatch.match.domain.repository.TeamRepository;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Slice;

@DataJpaTest
@Import(ReportService.class)
class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private TeamRepository teamRepository;

    private Team reporter;
    private Team target;
    private ReportRequest reportRequest;
    private Report report;

    @BeforeEach
    void setUp() {
        // Set up Teams
        reporter = Team.builder()
                .account("reporterAccount")
                .password("password")
                .teamName("Reporter Team")
                .teamDescription("Description of Reporter Team")
                .region("Region A")
                .gender(Gender.MALE)
                .role(Role.USER)
                .build();

        target = Team.builder()
                .account("targetAccount")
                .password("password")
                .teamName("Target Team")
                .teamDescription("Description of Target Team")
                .region("Region B")
                .gender(Gender.FEMALE)
                .role(Role.USER)
                .build();

        teamRepository.save(reporter);
        teamRepository.save(target);

        // Set up Report
        report = Report.builder()
                .reporter(reporter)
                .target(target)
                .title("Test Title")
                .content("Test Content")
                .build();
        reportRepository.save(report);

        // Set up ReportRequest
        reportRequest = new ReportRequest();
        reportRequest.setReporterId(reporter.getId());
        reportRequest.setTargetId(target.getId());
        reportRequest.setTitle("Test Title");
        reportRequest.setContent("Test Content");
    }

    @AfterEach
    void clear() {
        reportRepository.deleteAll();
        teamRepository.deleteAll();
    }

    @Test
    void getReportsNoIdReturnsFirstPage() {
        Slice<Report> result = reportService.getReports(null);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Test Title");
    }

    @Test
    void getReportsWithIdReturnsNextPage() {
        Long lastId = 0L;
        for (int i = 0; i < 19; i++) {
            report = Report.builder()
                    .reporter(reporter)
                    .target(target)
                    .title("Test Title")
                    .content("Test Content")
                    .build();
            reportRepository.save(report);
        }
        for (int i = 0; i < 3; i++) {
            report = Report.builder()
                    .reporter(reporter)
                    .target(target)
                    .title("Test Title")
                    .content("Test Content")
                    .build();
            lastId = reportRepository.save(report).getId();
        }


        Slice<Report> result = reportService.getReports(lastId-19);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(3);
    }

    @Test
    void createReportValidRequestCreatesReport() {
        reportService.createReport(reportRequest);

        Optional<Report> savedReport = reportRepository.findById(report.getId() + 1);
        assertThat(savedReport).isPresent();
        assertThat(savedReport.get().getTitle()).isEqualTo("Test Title");
        assertThat(savedReport.get().getContent()).isEqualTo("Test Content");
        assertThat(savedReport.get().getReporter()).isEqualTo(reporter);
        assertThat(savedReport.get().getTarget()).isEqualTo(target);
    }

    @Test
    void createReportInvalidReporterThrowsException() {
        reportRequest.setReporterId(-1L); // Invalid ID

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            reportService.createReport(reportRequest);
        });

        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 유저 or 권한이 없음");
    }

    @Test
    void createReportInvalidTargetThrowsException() {
        reportRequest.setTargetId(-1L); // Invalid ID

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            reportService.createReport(reportRequest);
        });

        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 유저 or 권한이 없음");
    }
}
