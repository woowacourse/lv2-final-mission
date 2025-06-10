package finalmission.exception;

import org.springframework.http.HttpStatus;

public class ReservationNotFoundException extends ApplicationException {

    private static final String MESSAGE = "존재하지 않는 예약입니다.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public ReservationNotFoundException() {
        super(HTTP_STATUS, MESSAGE);
    }
}
