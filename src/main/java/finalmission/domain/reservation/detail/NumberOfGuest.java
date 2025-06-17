package finalmission.domain.reservation.detail;

import jakarta.persistence.Embeddable;

@Embeddable
public class NumberOfGuest {

    private int numberOfGuest;

    public NumberOfGuest(final int numberOfGuest) {
        this.numberOfGuest = numberOfGuest;
    }

    protected NumberOfGuest() {
    }

    public int getValue() {
        return numberOfGuest;
    }

    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof final NumberOfGuest numberOfGuest1)) {
            return false;
        }

        return numberOfGuest == numberOfGuest1.numberOfGuest;
    }

    @Override
    public int hashCode() {
        return numberOfGuest;
    }
}
