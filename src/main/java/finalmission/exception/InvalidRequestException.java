package finalmission.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException() {
    }

    public InvalidRequestException(final String message) {
        super(message);
    }
}
