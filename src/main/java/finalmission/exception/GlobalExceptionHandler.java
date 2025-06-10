package finalmission.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String EXCEPTION_HEADER = "[ERROR] ";

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.badRequest()
                .body(EXCEPTION_HEADER + e.getMessage());
    }
}
