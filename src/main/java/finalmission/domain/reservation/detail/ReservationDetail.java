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

    public ReservationDetail(final NumberOfGuest numberOfGuest, final Message reservatonMessage) {
        this.numberOfGuest = numberOfGuest;
        this.reservatonMessage = reservatonMessage;
    }

    public ReservationDetail(final int numberOfGuest, final String message) {
        this(new NumberOfGuest(numberOfGuest), new Message(message));
    }

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
