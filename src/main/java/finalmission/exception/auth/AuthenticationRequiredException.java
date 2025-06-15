package finalmission.exception.auth;

import finalmission.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class AuthenticationRequiredException extends ApplicationException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;
    private static final String MESSAGE = "로그인이 필요합니다.";

    public AuthenticationRequiredException() {
        super(HTTP_STATUS, MESSAGE);
    }
}
