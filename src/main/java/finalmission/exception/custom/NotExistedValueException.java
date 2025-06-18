package finalmission.exception.custom;

public class NotExistedValueException extends IllegalArgumentException {

    public NotExistedValueException(final String message) {
        super(message);
    }
}
