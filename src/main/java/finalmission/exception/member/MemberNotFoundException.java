package finalmission.exception.member;

import finalmission.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends ApplicationException {

    private static final String MESSAGE = "회원이 존재하지 않습니다.";
    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public MemberNotFoundException() {
        super(STATUS, MESSAGE);
    }
}
