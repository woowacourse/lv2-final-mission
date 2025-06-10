package finalmission.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationRequiredException extends ApplicationException {

    private static final String MESSAGE = "인증이 필요합니다.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;

    public AuthenticationRequiredException() {
        super(HTTP_STATUS, MESSAGE);
    }
}
