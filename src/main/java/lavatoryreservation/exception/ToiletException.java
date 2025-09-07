package lavatoryreservation.exception;

public class ToiletException extends IllegalArgumentException {

    public ToiletException(String message) {
        throw new IllegalArgumentException(message);
    }
}
