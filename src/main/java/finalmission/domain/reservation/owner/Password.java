package finalmission.domain.reservation.owner;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Password {

    private String password;

    public Password(final String password) {
        this.password = password;
    }

    protected Password() {

    }

    public String getValue() {
        return password;
    }

    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof final Password password1)) {
            return false;
        }

        return Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(password);
    }
}
