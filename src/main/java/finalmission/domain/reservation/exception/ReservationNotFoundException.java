package finalmission.domain.reservation.exception;

import finalmission.infrastructure.exception.NotFoundException;

public class ReservationNotFoundException extends NotFoundException {
    public ReservationNotFoundException(final String message) {
        super(message);
    }
}
