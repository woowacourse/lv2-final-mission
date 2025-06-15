package finalmission.member.domain.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public record Name(String value) {
    public Name {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("이름이 설정되지 않았습니다.");
        }

        if (value.length() > 10) {
            throw new IllegalArgumentException("이름은 최대 10글자까지 설정 가능합니다.");
        }
    }
}
