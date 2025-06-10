package finalmission.exception;

import org.springframework.http.HttpStatus;

public class EmailNotExistException extends ApplicationException {

    private static final String MESSAGE = "존재하지 않는 이메일입니다.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public EmailNotExistException() {
        super(HTTP_STATUS, MESSAGE);
    }
}
