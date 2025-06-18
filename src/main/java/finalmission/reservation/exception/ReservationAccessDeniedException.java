package finalmission.reservation.exception;

import lombok.Getter;

@Getter
public class ReservationAccessDeniedException extends RuntimeException {

    public ReservationAccessDeniedException(String message) {
        super(message);
    }
}
