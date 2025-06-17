package finalmission.global.exception;

public class UnauthorizedException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "인가 문제 발생";

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException() {
        this(DEFAULT_MESSAGE);
    }
}
