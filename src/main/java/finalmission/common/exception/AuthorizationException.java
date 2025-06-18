package finalmission.common.exception;

public class AuthorizationException extends CustomException {

    public AuthorizationException(String message) {
        super(ErrorCode.FORBIDDEN, message);
    }
}
