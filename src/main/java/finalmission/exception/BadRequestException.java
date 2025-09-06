package finalmission.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends CakeDomainException{

    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public BadRequestException(String message, String errorCode) {
        super(message, HTTP_STATUS, errorCode);
    }

    public static BadRequestException pickUpTimeInvalid() {
        return new BadRequestException("해당 날짜와 시간에는 예약이 불가합니다.", "PICKUP_TIME_INVALID");
    }

    public static BadRequestException letteringTooLong() {
        return new BadRequestException("레터링은 15자까지만 가능합니다.", "LETTERING_TOO_LONG");
    }
}
