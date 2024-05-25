package com.matchingMatch.admin;


import com.matchingMatch.match.domain.BaseEntity;
import com.matchingMatch.match.domain.Team;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //
    @ManyToOne(fetch = FetchType.LAZY)
    private Team reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team target;

    private String title;

    private String content;

    @Builder
    public Report(Team reporter, String title, String content, Team target) {
        this.reporter = reporter;
        this.title = title;
        this.content = content;
        this.target = target;
    }
}
