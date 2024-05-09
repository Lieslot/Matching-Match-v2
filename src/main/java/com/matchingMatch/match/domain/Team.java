package com.matchingMatch.match.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.matchingMatch.match.domain.enums.Gender;
import com.matchingMatch.match.domain.enums.Role;
import com.matchingMatch.notification.domain.MannerRateNotification;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
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

    @JsonIgnore
    @Column(unique = true, nullable = false)
    private String account;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String teamName;

    private String teamDescription;

    private String teamLogoUrl;

    private Long mannerPointSum = 0L;

    private Long matchCount = 0L;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private Gender gender;

    @Column
    private Role role;

    @OneToMany(mappedBy = "host")
    private List<Match> hostedMatches = new ArrayList<>();

    @OneToMany(mappedBy = "participant")
    private List<Match> participatedMatches = new ArrayList<>();

    @OneToMany(mappedBy = "targetTeam")
    private List<MannerRateNotification> unratedMatchesPush = new ArrayList<>();

    @Builder
    public Team(String account, String password, String teamName, String teamDescription, String teamLogoUrl,
                Long mannerPointSum, Long matchCount, String region, Gender gender, Role role,
                List<Match> hostedMatches,
                List<Match> participatedMatches) {
        this.account = account;
        this.password = password;
        this.teamName = teamName;
        this.teamDescription = teamDescription;
        this.teamLogoUrl = teamLogoUrl;
        this.region = region;
        this.gender = gender;
        this.role = role;
        this.hostedMatches = hostedMatches;
        this.participatedMatches = participatedMatches;
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


    // TODO 상대팀을 평가하는 로직
    public void isRatedAfterMatch(Long mannerPoint) {
        mannerPointSum += mannerPoint;
        matchCount++;
    }
}
