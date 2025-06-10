package finalmission.application.support.exception;

public class JwtExtractException extends RuntimeException {
    public JwtExtractException(String message) {
        super(message);
    }

    public JwtExtractException(String message, Throwable cause) {
        super(message, cause);
    }
}
