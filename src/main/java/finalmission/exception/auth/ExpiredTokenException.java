package finalmission.exception.auth;

import finalmission.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class ExpiredTokenException extends ApplicationException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;
    private static final String MESSAGE = "토큰이 만료되었습니다.";

    public ExpiredTokenException() {
        super(HTTP_STATUS, MESSAGE);
    }
}
