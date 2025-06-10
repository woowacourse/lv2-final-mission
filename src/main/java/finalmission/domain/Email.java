package finalmission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;

@Embeddable
public record Email(
        @Column(name = "email")
        String value
) {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    public Email {
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("이메일 형식이 아닙니다.");
        }
    }
}
