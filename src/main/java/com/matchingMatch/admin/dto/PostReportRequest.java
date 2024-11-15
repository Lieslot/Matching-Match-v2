package com.matchingMatch.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostReportRequest {

    private Long reporterId;

    private Long targetId;

    private String title;

    private String content;



}
