package finalmission.exception;

public class BusinessException extends RuntimeException {

    private static final String ERROR_PREFIX = "[ERROR] ";

    public BusinessException(String message) {
        super(ERROR_PREFIX + message);
    }
}
