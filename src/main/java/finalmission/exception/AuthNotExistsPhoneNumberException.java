package finalmission.exception;

public class AuthNotExistsPhoneNumberException extends  RuntimeException {
    public AuthNotExistsPhoneNumberException() {
    }

    public AuthNotExistsPhoneNumberException(String message) {
        super(message);
    }
}
