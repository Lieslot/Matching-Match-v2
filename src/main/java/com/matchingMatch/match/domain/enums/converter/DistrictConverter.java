package com.matchingMatch.match.domain.enums.converter;

import com.matchingMatch.match.domain.enums.SeoulDistrict;
import jakarta.persistence.AttributeConverter;

public class DistrictConverter implements AttributeConverter<SeoulDistrict, String> {

    // 구의 이름이 변화하는 경우가 있을 수 있기 때문에 enum 자체 필드를 column으로 함
    @Override
    public String convertToDatabaseColumn(SeoulDistrict seoulDistrict) {

        if (seoulDistrict == null) {
            return null;
        }

        return seoulDistrict.name();
    }

    @Override
    public SeoulDistrict convertToEntityAttribute(String s) {

        return SeoulDistrict.of(s);
    }
}
