package com.matchingMatch.match.domain.enums.converter;

import com.matchingMatch.match.domain.enums.Gender;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;


@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, String> {


    @Override
    public String convertToDatabaseColumn(Gender gender) {

        if (gender == null) {
            return null;
        }

        return gender.getValue();
    }

    @Override
    public Gender convertToEntityAttribute(String s) {
        return Stream.of(Gender.values())
                .filter(g -> g.getValue().equals(s))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
