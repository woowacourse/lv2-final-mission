package finalmission.member.domain.utils;

import finalmission.member.domain.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role attribute) {
        return attribute.getCode();
    }

    @Override
    public Role convertToEntityAttribute(String dbData) {
        return Role.ofCode(dbData);
    }
}
