package finalmission.reservation.exception;

import finalmission.global.exception.BadRequestException;

public class ReservationBadRequestException extends BadRequestException {

    private static final String DEFAULT_MESSAGE = "예약에 있어서 잘못된 요청입니다.";

    public ReservationBadRequestException(String message) {
        super(message);
    }

    public ReservationBadRequestException() {
        this(DEFAULT_MESSAGE);
    }
}
