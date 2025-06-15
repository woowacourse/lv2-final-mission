package finalmission.exception.auth;

import finalmission.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends ApplicationException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;
    private static final String MESSAGE = "잘못된 토큰입니다.";

    public InvalidTokenException() {
        super(HTTP_STATUS, MESSAGE);
    }
}
