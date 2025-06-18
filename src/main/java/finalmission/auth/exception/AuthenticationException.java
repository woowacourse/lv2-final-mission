package finalmission.auth.exception;

public class AuthenticationException extends IllegalArgumentException {

    public AuthenticationException(String message) {
        super("[ERROR] 403 Forbidden : " + message);
    }
}
