package finalmission.auth.exception;

import finalmission.global.exception.BadRequestException;

public class InvalidTokenException extends BadRequestException {

    private static final String DEFAULT_MESSAGE = "유효하지 않은 토큰입니다.";

    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException() {
        this(DEFAULT_MESSAGE);
    }
}
