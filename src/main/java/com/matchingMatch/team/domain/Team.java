package com.matchingMatch.team.domain;


import com.matchingMatch.match.domain.BaseEntity;
import com.matchingMatch.match.domain.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String teamDescription;

    private String teamLogoUrl;
    @Column(nullable = false)
    private Long leaderId;

    private Long mannerPointSum = 0L;

    private Long matchCount = 0L;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private Gender gender;



    @Builder
    public Team(String teamName, String teamDescription, String teamLogoUrl,
                String region, Gender gender, Long leaderId) {

        this.name = teamName;
        this.teamDescription = teamDescription;
        this.teamLogoUrl = teamLogoUrl;
        this.region = region;
        this.gender = gender;
        this.leaderId = leaderId;

    }

    public Team() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Team team = (Team) o;
        return Objects.equals(id, team.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


    // TODO 이미 평가한 경우 무시하도록 예외처리
    public void rate(Long mannerPoint) {
        mannerPointSum += mannerPoint;
        matchCount++;
    }

//    public void addNotification(Notification notification) {
//        this.notifications.add(notification);
//    }

//    public void confirmParticipant(Match match) {
//
//        participatedMatches.add(match);
//
//    }
//
//    public void cancelParticipant(Match match) {
//
//        participatedMatches.add(match);
//
//    }
//
//    public void addMatchBookMark(MatchBookmark matchBookMark) {
//        matchBookmarks.add(matchBookMark);
//    }
//
//    public void removeMatchBookmark(Long matchBookmarkId) {
//        matchBookmarks.removeIf(matchBookmark -> matchBookmark.getId().equals(matchBookmarkId));
//    }

    public float calculateMannerPoint() {
        return (float) this.mannerPointSum / this.matchCount;
    }
}
