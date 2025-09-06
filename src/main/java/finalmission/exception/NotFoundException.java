package finalmission.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CakeDomainException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public NotFoundException(String message, String errorCode) {
        super(message, HTTP_STATUS, errorCode);
    }


    public static NotFoundException memberNotFound() {
        return new NotFoundException("회원이 존재하지 않습니다.", "MEMBER_NOT_FOUND");
    }

    public static NotFoundException timeNotFound() {
        return new NotFoundException("해당 시간이 존재하지 않습니다.", "TIME_NOT_FOUND");
    }

    public static NotFoundException flavorNotFound() {
        return new NotFoundException("해당 맛이 존재하지 않습니다.", "FLAVOR_NOT_FOUND");
    }

    public static NotFoundException sizeNotFound() {
        return new NotFoundException("해당 사이즈가 존재하지 않습니다.", "SIZE_NOT_FOUND");
    }

    public static NotFoundException cakeNotFound() {
        return new NotFoundException("해당 케이크가 존재하지 않습니다.", "CAKE_NOT_FOUND");
    }

    public static NotFoundException reservationNotFound() {
        return new NotFoundException("해당 예약이 존재하지 않습니다.", "RESERVATION_NOT_FOUND");
    }
}
