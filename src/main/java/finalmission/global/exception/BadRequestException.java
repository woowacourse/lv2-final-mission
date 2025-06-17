package finalmission.global.exception;

public class BadRequestException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "잘못된 요청입니다.";

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException() {
        this(DEFAULT_MESSAGE);
    }
}
