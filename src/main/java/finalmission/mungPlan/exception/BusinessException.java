package finalmission.mungPlan.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends CustomException {
    public BusinessException(String message) {
        super("비즈니스 규칙 위반: " + message, HttpStatus.BAD_REQUEST);
    }
}
