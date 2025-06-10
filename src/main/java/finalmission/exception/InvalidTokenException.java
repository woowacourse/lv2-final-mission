package finalmission.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends ApplicationException {

    private static final String MESSAGE = "잘못된 토큰입니다.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;

    public InvalidTokenException() {
        super(HTTP_STATUS, MESSAGE);
    }
}
