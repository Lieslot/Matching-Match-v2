package com.matchingMatch.admin.dto;

import com.matchingMatch.match.domain.Team;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportRequest {

    private Long reporterId;

    private Long targetId;

    private String title;

    private String content;



}
