package com.matchingMatch.match.domain.entity;

import com.matchingMatch.match.domain.enums.SeoulDistrict;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class StadiumEntity {

    public StadiumEntity() {

    }

    @Builder
    public StadiumEntity(String name, SeoulDistrict district, String isParkPossible, String address) {
        this.name = name;
        this.district = district;
        this.isParkPossible = isParkPossible;
        this.address = address;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private SeoulDistrict district;
    @Column(nullable = false)
    private String isParkPossible;
    @Column(nullable = false)
    private String address;


}
