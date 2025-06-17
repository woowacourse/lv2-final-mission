package finalmission.domain.reservation.owner;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import java.util.Objects;

@Embeddable
public class Owner {

    @Embedded
    private Nickname nickname;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    public Owner() {
    }

    public Owner(final Nickname nickname, final Email email, final Password password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public Owner(final Nickname nickname, final String email, final String password) {
        this(nickname, new Email(email), new Password(password));
    }

    public Nickname getNickname() {
        return nickname;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }

    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof final Owner owner)) {
            return false;
        }

        return Objects.equals(getNickname(), owner.getNickname()) && Objects.equals(getEmail(),
                owner.getEmail()) && Objects.equals(getPassword(), owner.getPassword());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getNickname());
        result = 31 * result + Objects.hashCode(getEmail());
        result = 31 * result + Objects.hashCode(getPassword());
        return result;
    }
}
