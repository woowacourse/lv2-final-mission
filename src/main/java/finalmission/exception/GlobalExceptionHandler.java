package finalmission.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CakeDomainException.class)
    public ResponseEntity<ErrorResponse> handleCakeDomainException(CakeDomainException exception) {
        return ResponseEntity.status(exception.getHttpStatus()).body(ErrorResponse.from(exception));
    }

    record ErrorResponse(
            HttpStatus httpStatus,
            String errorCode,
            String message
    ) {

        public static ErrorResponse from(CakeDomainException exception) {
            return new ErrorResponse(exception.getHttpStatus(), exception.getErrorCode(), exception.getMessage());
        }
    }
}
