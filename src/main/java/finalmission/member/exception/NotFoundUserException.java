package finalmission.member.exception;

import finalmission.global.exception.NotFoundException;

public class NotFoundUserException extends NotFoundException {

    private static final String DEFAULT_MESSAGE = "유저를 찾을 수 없습니다.";

    public NotFoundUserException(String message) {
        super(message);
    }

    public NotFoundUserException() {
        this(DEFAULT_MESSAGE);
    }
}
