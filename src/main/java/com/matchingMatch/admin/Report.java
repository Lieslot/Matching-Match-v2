package com.matchingMatch.admin;


import com.matchingMatch.match.domain.BaseEntity;
import com.matchingMatch.team.domain.Team;
import jakarta.persistence.Column;
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
    @Column(nullable = false)
    private Long authorId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;


    @Builder
    public Report(Long authorId, String title, String content, Team target) {
        this.authorId = authorId;
        this.title = title;
        this.content = content;
    }
}
