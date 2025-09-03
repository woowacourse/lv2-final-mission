package finalmission.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
    }

    public AuthenticationException(final String message) {
        super(message);
    }
}
