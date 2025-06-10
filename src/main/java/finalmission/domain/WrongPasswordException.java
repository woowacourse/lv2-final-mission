package finalmission.domain;

import finalmission.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class WrongPasswordException extends ApplicationException {

    private static final String MESSAGE = "잘못된 비밀번호입니다.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public WrongPasswordException() {
        super(HTTP_STATUS, MESSAGE);
    }
}
