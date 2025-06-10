package finalmission.domain.reservation.exception;

import finalmission.infrastructure.exception.DuplicateException;

public class DuplicateReservationException extends DuplicateException {
    public DuplicateReservationException(final String message) {
        super(message);
    }
}
