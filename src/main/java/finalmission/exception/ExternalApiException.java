package finalmission.exception;

public class ExternalApiException extends RuntimeException {
    public ExternalApiException() {
    }

    public ExternalApiException(final String message) {
        super(message);
    }
}
