package finalmission.member.domain.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public record Password(String value) {

    public Password {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("비밀번호가 입력되지 않았습니다.");
        }

        if (value.length() < 8) {
            throw new IllegalArgumentException("비밀번호는 최소 8글자 이상으로 설정해야 합니다.");
        }
    }
}
