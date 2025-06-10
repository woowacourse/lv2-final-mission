package finalmission.exception;

public class AuthNotValidTokenException extends RuntimeException {
    public AuthNotValidTokenException() {
    }

    public AuthNotValidTokenException(String message) {
        super(message);
    }
}
