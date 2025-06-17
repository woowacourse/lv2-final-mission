package finalmission.global.error.handler;

import finalmission.global.error.dto.ErrorResponse;
import finalmission.global.error.exception.BadRequestException;
import finalmission.global.error.exception.ForbiddenException;
import finalmission.global.error.exception.NotFoundException;
import finalmission.global.error.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(exception = RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        log.error("RuntimeException: {}", e.getMessage());
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(httpStatus).body(new ErrorResponse(httpStatus.value(), e.getMessage()));
    }

    @ExceptionHandler(exception = BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e) {
        log.error("BadRequestException: {}", e.getMessage());
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).body(new ErrorResponse(httpStatus.value(), e.getMessage()));
    }

    @ExceptionHandler(exception = ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException e) {
        log.error("ForbiddenException: {}", e.getMessage());
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(httpStatus).body(new ErrorResponse(httpStatus.value(), e.getMessage()));
    }

    @ExceptionHandler(exception = NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        log.error("NotFoundException: {}", e.getMessage());
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(httpStatus).body(new ErrorResponse(httpStatus.value(), e.getMessage()));
    }

    @ExceptionHandler(exception = ServerException.class)
    public ResponseEntity<ErrorResponse> handleServerException(ServerException e) {
        log.error("ServerException: {}", e.getMessage());
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(httpStatus).body(new ErrorResponse(httpStatus.value(), e.getMessage()));
    }
}
