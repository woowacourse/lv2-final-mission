package finalmission.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(final HttpServletRequest request, final CustomException e) {
        log.error("예외 발생 : {}", e.getMessage(), e);
        final ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus().value()).body(ErrorResponse.of(request, errorCode));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final HttpServletRequest request, final Exception e) {
        log.error("정의되지 않은 예외 발생 : {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().body(ErrorResponse.of(request, ErrorCode.INTERNAL_SERVER_ERROR));
    }
}
