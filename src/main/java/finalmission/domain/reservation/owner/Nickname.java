package finalmission.domain.reservation.owner;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Nickname {

    private String nickname;

    public Nickname(final String nickname) {
        this.nickname = nickname;
    }

    protected Nickname() {
    }

    public String getValue() {
        return nickname;
    }

    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof final Nickname nickname1)) {
            return false;
        }

        return Objects.equals(nickname, nickname1.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nickname);
    }
}
