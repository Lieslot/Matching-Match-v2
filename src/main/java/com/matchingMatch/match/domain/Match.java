package com.matchingMatch.match.domain;


import static jakarta.persistence.CascadeType.*;

import com.matchingMatch.match.domain.enums.Gender;
import com.matchingMatch.team.domain.Team;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
@Setter
public class Match extends BaseEntity {

    private static final String INVALID_AUTHORITY = "권한이 없는 접근입니다.";

    public Match() {

    }

    @Builder
    public Match(Team host, Team participant, LocalDateTime startTime, LocalDateTime endTime, Gender gender,
                 int stadiumCost, String etc) {
        this.host = host;
        this.participant = participant;
        this.startTime = startTime;
        this.endTime = endTime;
        this.gender = gender;
        this.stadiumCost = stadiumCost;
        this.etc = etc;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "host_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Team host;

    @JoinColumn(name = "participant_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Team participant;

    @OneToMany(mappedBy = "targetMatch", fetch = FetchType.LAZY,
            cascade = {REMOVE, PERSIST, MERGE}, orphanRemoval = true)
    private Set<MatchRequest> matchRequests = new HashSet<>();

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd:hh:mm:ss")
    private LocalDateTime startTime;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd:hh:mm:ss")
    private LocalDateTime endTime;

    @Column(nullable = false)
    private Gender gender;

    private int stadiumCost;

    private String etc;

    @OneToOne(mappedBy = "matchId", fetch = FetchType.LAZY,
            cascade = {PERSIST, MERGE, REMOVE},
            orphanRemoval = true)
    private MannerRateCheck matchRateCheck = new MannerRateCheck();


    public void addRequestTeam(MatchRequest matchRequest) {

        if (matchRequests.contains(matchRequest)) {
            throw new IllegalArgumentException("이미 신청한 매치입니다.");
        }

        matchRequests.add(matchRequest);
    }

    public void removeMatchRequest(MatchRequest matchRequest) {
        if (!matchRequests.contains(matchRequest)) {
            throw new IllegalArgumentException("신청하지 않았던 매치입니다.");
        }

        matchRequests.remove(matchRequest);
    }

    public void checkHostEqualTo(Long target) {

        if (!host.getId().equals(target)) {
            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }

    }

    public void checkParticipantEqualTo(Long target) {

        if (!participant.getId().equals(target)) {
            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }

    }

    public void checkInvolvedInMatch(Long target) {
        if (!host.getId().equals(target) && !participant.getId().equals(target)) {

            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }
    }

    public void rateMannerPoint(Long userId, Long mannerPoint) {

        if (host.getId().equals(userId)) {
            matchRateCheck.checkParticipant();
            participant.isRatedAfterMatch(mannerPoint);
        }

        else if (participant.getId().equals(userId)) {
            matchRateCheck.checkHost();
            host.isRatedAfterMatch(mannerPoint);
        }

        else {
            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }

    }
}
