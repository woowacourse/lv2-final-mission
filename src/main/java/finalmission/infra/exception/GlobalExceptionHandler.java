package finalmission.infra.exception;

import finalmission.infra.thirdparty.OpenApiException;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "알 수 없는 문제가 발생했습니다.";

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e) {
        String message = e.getMessage();
        log.info(message);
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.NOT_FOUND, message);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        String message = e.getMessage();
        log.warn(message);
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.CONFLICT, message);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException e) {
        String message = e.getMessage();
        log.warn(message);
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, message);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(OpenApiException.class)
    public ResponseEntity<ErrorResponse> handleOpenApiException(OpenApiException e) {
        String message = e.getMessage();
        log.warn(message);
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, message);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception e) {
        String message = INTERNAL_SERVER_ERROR_MESSAGE;
        log.error(message, e);
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, message);
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
