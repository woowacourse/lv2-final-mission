package finalmission.exception.member;

import finalmission.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends ApplicationException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE = "중복된 이메일입니다.";

    public DuplicateEmailException() {
        super(HTTP_STATUS, MESSAGE);
    }
}
