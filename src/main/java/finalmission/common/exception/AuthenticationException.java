package finalmission.common.exception;

public class AuthenticationException extends CustomException {

    public AuthenticationException(String message) {
        super(ErrorCode.UNAUTHORIZED, message);
    }
}
