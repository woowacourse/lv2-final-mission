package finalmission.exception.reservation;

import finalmission.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class NotMyReservationException extends ApplicationException {

    private static final String MESSAGE = "자신의 예약이 아닙니다.";
    private static final HttpStatus STATUS = HttpStatus.FORBIDDEN;

    public NotMyReservationException() {
        super(STATUS, MESSAGE);
    }
}
