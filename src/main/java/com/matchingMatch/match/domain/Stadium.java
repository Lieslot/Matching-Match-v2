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
public class Stadium {

    public Stadium() {

    }

    @Builder
    public Stadium(String name, SeoulDistrict district, String isParkPossible, String address) {
        this.name = name;
        this.district = district;
        this.isParkPossible = isParkPossible;
        this.address = address;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private SeoulDistrict district;

    private String isParkPossible;

    private String address;


}
