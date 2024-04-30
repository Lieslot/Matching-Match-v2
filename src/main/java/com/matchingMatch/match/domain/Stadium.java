package com.matchingMatch.match.domain;

import com.matchingMatch.match.domain.enums.SeoulDistrict;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
public class Stadium {

    public Stadium() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private SeoulDistrict district;

    private String isParkPossible;

    private String address;



}
