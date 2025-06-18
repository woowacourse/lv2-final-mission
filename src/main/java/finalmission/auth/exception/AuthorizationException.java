package finalmission.auth.exception;

public class AuthorizationException extends IllegalArgumentException {

    public AuthorizationException(String message) {
        super("[ERROR] 401 Unauthorized : " + message);
    }
}
