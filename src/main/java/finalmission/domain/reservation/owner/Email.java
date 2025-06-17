package finalmission.domain.reservation.owner;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Email {

    private String email;

    public Email(final String email) {
        this.email = email;
    }

    protected Email() {

    }

    public String getValue() {
        return email;
    }

    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof final Email email1)) {
            return false;
        }

        return Objects.equals(email, email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}
