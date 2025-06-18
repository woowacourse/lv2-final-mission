package finalmission.global.exception;

public class MissingLoginException extends RuntimeException {
    public MissingLoginException(String message) {
        super(message);
    }

    public MissingLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
