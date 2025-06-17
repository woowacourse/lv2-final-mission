package finalmission.global.exception;

public class NotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "찾을 수 없습니다.";

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException() {
        this(DEFAULT_MESSAGE);
    }
}
