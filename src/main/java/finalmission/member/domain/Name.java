package finalmission.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Name {
    public static final String VALID_NAME_REGEX = "[a-zA-Z가-힣0-9 ]+";

    @Column(name = "name", nullable = false)
    private String value;

    public Name(String value) {
        validateName(value);
        this.value = value;
    }

    private void validateName(String name) {
        validateNotBlank(name);
        validateRegex(name);
    }

    private void validateNotBlank(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 빈 값일 수 없습니다.");
        }
    }

    private void validateRegex(String name) {
        if (!name.matches(VALID_NAME_REGEX)) {
            throw new IllegalArgumentException("이름은 특수문자를 사용할 수 없습니다.");
        }
    }
}
