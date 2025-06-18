package finalmission.exception;

import org.springframework.http.HttpStatus;

public class LoginFailedException extends CustomException {

    public LoginFailedException() {
        super("로그인에 실패했습니다.", HttpStatus.UNAUTHORIZED);
    }
}
