package finalmission.exception.custom;

public class NotAuthenticatedException extends IllegalArgumentException {

    public NotAuthenticatedException(final String s) {
        super(s);
    }
}
