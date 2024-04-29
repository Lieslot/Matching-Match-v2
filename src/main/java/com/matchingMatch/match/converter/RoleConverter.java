package com.matchingMatch.match.converter;

import com.matchingMatch.match.domain.Gender;
import com.matchingMatch.match.domain.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;


@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {


    @Override
    public String convertToDatabaseColumn(Role role) {

        if (role == null) {
            return null;
        }

        return role.getName();

    }

    @Override
    public Role convertToEntityAttribute(String s) {

        return Stream.of(Role.values())
                .filter(g -> g.getName().equals(s))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
