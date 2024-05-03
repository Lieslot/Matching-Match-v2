package com.matchingMatch.admin;


import com.matchingMatch.match.domain.BaseEntity;
import com.matchingMatch.match.domain.Team;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Report extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    private String title;

    private String content;

    private Long hits;

    @Builder
    public Report(Team team, String title, String content, Long hits) {
        this.team = team;
        this.title = title;
        this.content = content;
        this.hits = hits;
    }
}
