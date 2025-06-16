package finalmission.presentation.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(
        final Exception ex,
        final WebRequest request
    ) {
        log.error("서버 내부 오류: {}", ex.getMessage(), ex);

        return createProblemResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 알 수 없는 오류가 발생했습니다.", request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handle(
        final IllegalArgumentException ex,
        final WebRequest request
    ) {
        return createProblemResponse(ex, HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handle(
        final IllegalStateException ex,
        final WebRequest request
    ) {
        return createProblemResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
    }

    private ResponseEntity<Object> createProblemResponse(
        final Exception ex,
        final HttpStatus status,
        final String message,
        final WebRequest request
    ) {
        final ProblemDetail body = super.createProblemDetail(ex, status, message, null, null, request);

        return super.handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
    }
}
