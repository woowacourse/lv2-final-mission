package finalmission.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends CakeDomainException{

    private static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;

    public ConflictException(String message, String errorCode) {
        super(message, HTTP_STATUS, errorCode);
    }

    public static ConflictException memberAlreadyExist() {
        return new ConflictException("이미 회원이 존재합니다.", "MEMBER_ALREADY_EXIST");
    }
}
