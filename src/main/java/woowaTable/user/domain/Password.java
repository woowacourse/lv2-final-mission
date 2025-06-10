package woowaTable.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

    @Column(name = "password")
    private String value;

    public Password(final String value) {
        this.value = value;
    }

    public static Password from(final String value) {
        return new Password(value);
    }
}
