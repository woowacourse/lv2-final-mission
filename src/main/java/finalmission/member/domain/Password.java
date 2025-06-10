package finalmission.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Password {
    @Column(name = "password", nullable = false)
    private String value;

    public Password(String rawValue, PasswordEncoder encoder) {
        validatePassword(rawValue);
        this.value = encoder.encode(rawValue);
    }

    private void validatePassword(String rawValue) {
        if (rawValue == null || rawValue.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 빈 값일 수 없습니다.");
        }
    }
}
