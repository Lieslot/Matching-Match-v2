package com.matchingMatch.match.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Team {

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

    @Column(nullable = false)
    private Role role;

}
