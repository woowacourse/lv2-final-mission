package finalmission.exception;

import org.springframework.http.HttpStatus;

public class ExpiredTokenException extends ApplicationException {

    private static final String MESSAGE = "만료된 토큰입니다.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;

    public ExpiredTokenException() {
        super(HTTP_STATUS, MESSAGE);
    }
}
