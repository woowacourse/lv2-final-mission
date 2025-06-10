package woowaTable.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {

    @Column(name = "email")
    private String value;

    public Email(final String value) {
        this.value = value;
    }

    public static Email from(final String value) {
        return new Email(value);
    }
}
