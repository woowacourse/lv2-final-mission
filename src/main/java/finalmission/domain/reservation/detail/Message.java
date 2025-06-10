package finalmission.domain.reservation.detail;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Message {

    private String message;

    public Message(final String message) {
        this.message = message;
    }

    protected Message() {
    }

    public String getMessage() {
        return message;
    }

    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof final Message message1)) {
            return false;
        }

        return Objects.equals(getMessage(), message1.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getMessage());
    }
}
