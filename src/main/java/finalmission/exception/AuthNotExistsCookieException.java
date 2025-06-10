package finalmission.exception;

public class AuthNotExistsCookieException extends RuntimeException {
    public AuthNotExistsCookieException() {
    }

    public AuthNotExistsCookieException(String message) {
        super(message);
    }
}
