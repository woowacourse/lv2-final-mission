package finalmission.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<String> handleException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}
