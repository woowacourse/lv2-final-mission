package finalmission.exception.auth;

import finalmission.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class LoginFailedException extends ApplicationException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE = "로그인에 실패했습니다.";

    public LoginFailedException() {
        super(HTTP_STATUS, MESSAGE);
    }
}
