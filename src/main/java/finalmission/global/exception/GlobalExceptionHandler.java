package finalmission.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingLoginException.class)
    public ResponseEntity<ErrorResponse> handleMissingLoginException(MissingLoginException e) {
        HttpStatusCode statusCode = HttpStatusCode.valueOf(HttpStatus.FORBIDDEN.value());
        ErrorResponse response = ErrorResponse.builder(e, statusCode, e.getMessage()).build();
        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        HttpStatusCode statusCode = HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value());
        ErrorResponse response = ErrorResponse.builder(e, statusCode, e.getMessage()).build();
        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception e) {
        HttpStatusCode statusCode = HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
        ErrorResponse response = ErrorResponse.builder(e, statusCode, e.getMessage()).build();
        return ResponseEntity.status(statusCode).body(response);
    }
}
