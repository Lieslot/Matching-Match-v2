package com.matchingMatch.match.domain.enums;

import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SeoulDistrict {
    N1("강남구"),
    N2("강동구"),
    N3("강북구"),
    N4("강서구"),
    N5("관악구"),
    N6("광진구"),
    N7("구로구"),
    N8("금천구"),
    N9("노원구"),
    N10("도봉구"),
    N11("동대문구"),
    N12("동작구"),
    N13("마포구"),
    N14("서대문구"),
    N15("서초구"),
    N16("성동구"),
    N17("성북구"),
    N18("송파구"),
    N19("양천구"),
    N20("영등포구"),
    N21("용산구"),
    N22("은평구"),
    N23("종로구"),
    N24("중구"),
    N25("중랑구");

    private final String name;


    public static SeoulDistrict of(String name) {
        return Stream.of(SeoulDistrict.values())
                .filter(district -> district.name.equals(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

    }


}
