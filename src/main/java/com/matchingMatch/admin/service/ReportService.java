package com.matchingMatch.admin.service;

import com.matchingMatch.admin.Report;
import com.matchingMatch.admin.domain.ReportRepository;
import com.matchingMatch.admin.dto.PostReportRequest;
import com.matchingMatch.team.domain.Team;
import com.matchingMatch.match.domain.repository.TeamRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final TeamRepository teamRepository;

    public Slice<Report> getReports(Long id) {
        PageRequest pageRequest = PageRequest.of(0, 20);

        if (id == null) {
            return reportRepository.findAll(pageRequest);
        }

        return reportRepository.findAllByIdLessThanOrderByIdDesc(id, pageRequest);

    }

    @Transactional
    public void createReport(PostReportRequest reportRequest) {

//        Long reporterId = reportRequest.getReporterId();
//        Long targetId = reportRequest.getTargetId();
//
//        Optional<Team> reporterSearchResult = teamRepository.findById(reporterId);
//        Optional<Team> targetSearchResult = teamRepository.findById(targetId);
//
//        if (reporterSearchResult.isEmpty() || targetSearchResult.isEmpty()) {
//            throw new IllegalArgumentException("존재하지 않는 유저 or 권한이 없음");
//        }
//
//        Report newReport = Report.builder()
//                .content(reportRequest.getContent())
//                .reporter(reporterSearchResult.get())
//                .target(targetSearchResult.get())
//                .title(reportRequest.getTitle())
//                .build();
//
//        reportRepository.save(newReport);

    }
}
