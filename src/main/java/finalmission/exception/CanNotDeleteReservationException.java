package finalmission.exception;

import org.springframework.http.HttpStatus;

public class CanNotDeleteReservationException extends ApplicationException {

    private static final String MESSAGE = "자신의 예약만 삭제할 수 있습니다.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;

    public CanNotDeleteReservationException() {
        super(HTTP_STATUS, MESSAGE);
    }
}
