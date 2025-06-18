package finalmission.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedMemberException extends CustomException {

    public UnauthorizedMemberException() {
        super("권한이 없습니다.", HttpStatus.UNAUTHORIZED);
    }
}
