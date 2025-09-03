package finalmission.exception;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
    }

    public AccessDeniedException(
            final String message
    ) {
        super(message);
    }
}
