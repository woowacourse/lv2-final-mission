package finalmission.exception;

import finalmission.exception.auth.AuthTokenNotFoundException;
import finalmission.exception.auth.AuthenticationException;
import finalmission.exception.auth.AuthorizationException;
import finalmission.exception.resource.AlreadyExistException;
import finalmission.exception.resource.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleResourceNotFoundException(final ResourceNotFoundException e) {
        final HttpStatus responseStatus = HttpStatus.NOT_FOUND;
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(responseStatus, e.getMessage());

        return ResponseEntity.status(responseStatus)
                .body(problemDetail);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ProblemDetail> handleAlreadyExistException(final AlreadyExistException e) {
        final HttpStatus responseStatus = HttpStatus.CONFLICT;
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(responseStatus, e.getMessage());

        return ResponseEntity.status(responseStatus)
                .body(problemDetail);
    }

    @ExceptionHandler(AuthTokenNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleAuthTokenNotFoundException(final AuthTokenNotFoundException e) {
        final HttpStatus responseStatus = HttpStatus.UNAUTHORIZED;
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(responseStatus, e.getMessage());

        return ResponseEntity.status(responseStatus)
                .body(problemDetail);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ProblemDetail> handleAuthenticationException(final AuthenticationException e) {
        final HttpStatus responseStatus = HttpStatus.UNAUTHORIZED;
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(responseStatus, e.getMessage());

        return ResponseEntity.status(responseStatus)
                .body(problemDetail);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ProblemDetail> handleAuthorizationException(final AuthorizationException e) {
        final HttpStatus responseStatus = HttpStatus.FORBIDDEN;
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(responseStatus, e.getMessage());

        return ResponseEntity.status(responseStatus)
                .body(problemDetail);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgumentException(final IllegalArgumentException e) {
        final HttpStatus responseStatus = HttpStatus.BAD_REQUEST;
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(responseStatus, e.getMessage());

        return ResponseEntity.status(responseStatus)
                .body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleException(final Exception e) {
        final Throwable root = getRootCause(e);
        log.error(e.getMessage());
        log.error("[ROOT CAUSE] {}: {}", root.getClass().getSimpleName(), root.getMessage());

        final HttpStatus responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(responseStatus, e.getMessage());

        return ResponseEntity.status(responseStatus)
                .body(problemDetail);
    }

    private Throwable getRootCause(final Throwable throwable) {
        Throwable cause = throwable.getCause();
        while (cause != null && cause.getCause() != null) {
            cause = cause.getCause();
        }
        return (cause != null) ? cause : throwable;
    }
}
