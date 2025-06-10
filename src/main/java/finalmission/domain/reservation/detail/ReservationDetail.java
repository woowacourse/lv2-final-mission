package finalmission.domain.reservation.detail;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import java.util.Objects;

@Embeddable
public class ReservationDetail {

    @Embedded
    private NumberOfGuest numberOfGuest;

    @Embedded
    private Message reservatonMessage;

    protected ReservationDetail() {
    }

    public NumberOfGuest getNumberOfGuest() {
        return numberOfGuest;
    }

    public Message getReservatonMessage() {
        return reservatonMessage;
    }

    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof final ReservationDetail that)) {
            return false;
        }

        return Objects.equals(getNumberOfGuest(), that.getNumberOfGuest()) && Objects.equals(
                getReservatonMessage(), that.getReservatonMessage());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getNumberOfGuest());
        result = 31 * result + Objects.hashCode(getReservatonMessage());
        return result;
    }
}
