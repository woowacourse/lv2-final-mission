package finalmission.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthNotExistsCookieException.class)
    public ResponseEntity<String> handleAuthNotExistsCookieException(AuthNotExistsCookieException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("인증 쿠키가 존재하지 않습니다. " + e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("서비스 예외가 발생했습니다. " + e.getMessage());
    }
}
