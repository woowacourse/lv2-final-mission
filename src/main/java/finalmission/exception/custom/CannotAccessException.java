package finalmission.exception.custom;

public class CannotAccessException extends IllegalStateException {

    public CannotAccessException(final String s) {
        super(s);
    }
}
