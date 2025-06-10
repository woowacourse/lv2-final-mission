package finalmission.exception.custom;

public class InvalidDateException extends IllegalArgumentException {

    public InvalidDateException(final String s) {
        super(s);
    }
}
