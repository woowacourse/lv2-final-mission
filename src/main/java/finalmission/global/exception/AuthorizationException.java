package finalmission.global.exception;

public class AuthorizationException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "권한 문제 발생";

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException() {
        this(DEFAULT_MESSAGE);
    }
}
