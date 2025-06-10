package finalmission.exception;

import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends ApplicationException {

    private static final String MESSAGE = "존재하지 않는 회원입니다..";
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public MemberNotFoundException() {
        super(HTTP_STATUS, MESSAGE);
    }
}
