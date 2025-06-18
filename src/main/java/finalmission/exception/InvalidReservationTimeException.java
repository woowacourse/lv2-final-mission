package finalmission.exception;

import org.springframework.http.HttpStatus;

public class InvalidReservationTimeException extends CustomException {
    public InvalidReservationTimeException() {
        super("예약은 08시 이후, 24시 이전만 가능합니다.", HttpStatus.BAD_REQUEST);
    }
}
