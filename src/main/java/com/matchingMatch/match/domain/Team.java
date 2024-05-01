package com.matchingMatch.match.domain;


import com.matchingMatch.match.domain.enums.Gender;
import com.matchingMatch.match.domain.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String account;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String teamName;

    private String teamDescription;

    private String teamLogoUrl;

    private Long mannerPointSum;

    private Long matchCount;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private Gender gender;

    @Column
    private Role role;

    @OneToMany(mappedBy = "hostId")
    private List<Match> hostedMatches;

    @OneToMany(mappedBy = "participantId")
    private List<Match> participatedMatches;


    public Team() {

    }
}
