package finalmission.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handelIllegalArgumentException(
        IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(400, e.getMessage()));
    }
}
