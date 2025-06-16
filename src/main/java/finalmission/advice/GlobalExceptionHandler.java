package finalmission.advice;

import finalmission.aop.LoggingAspect;
import finalmission.exception.BadRequestException;
import finalmission.exception.ExternalApiConnectionException;
import finalmission.exception.LoginException;
import finalmission.exception.NotFoundException;
import finalmission.exception.RandomNameGenerationException;
import finalmission.exception.UnauthorizedException;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> methodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException exception
    ) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        List<String> messages = fieldErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("올바르지 않은 입력입니다.");
        problemDetail.setDetail(String.join("\n", messages));
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ProblemDetail> handlerMethodValidationExceptionHandler(
            HandlerMethodValidationException exception
    ) {
        List<String> errorMessage = exception.getAllErrors().stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .toList();
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("올바르지 않은 입력입니다.");
        problemDetail.setDetail(String.join("\n", errorMessage));
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> httpMessageNotReadableExceptionHandler(
            HttpMessageNotReadableException exception
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("올바르지 않은 입력입니다.");
        problemDetail.setDetail("요청 메세지의 형식을 다시 확인해주세요.");
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ProblemDetail> handleBadRequestException(BadRequestException exception) {
        log.info("[INFO][{}]", LocalDateTime.now(), exception);
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("올바르지 않은 입력입니다.");
        problemDetail.setDetail(exception.getMessage());
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ProblemDetail> handleLoginException(LoginException exception) {
        log.info("[INFO][{}]", LocalDateTime.now(), exception);
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("로그인에 실패했습니다.");
        problemDetail.setDetail("로그인 정보를 다시 확인해주세요.");
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFoundException(NotFoundException exception) {
        log.info("[INFO][{}]", LocalDateTime.now(), exception);
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("데이터가 존재하지 않습니다.");
        problemDetail.setDetail(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(problemDetail);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ProblemDetail> handleUnauthorizedException(UnauthorizedException exception) {
        log.warn("[WARN][{}]", LocalDateTime.now(), exception);
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problemDetail.setTitle("인증을 먼저 진행해주세요.");
        problemDetail.setDetail(exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(problemDetail);
    }

    @ExceptionHandler(ExternalApiConnectionException.class)
    public ResponseEntity<ProblemDetail> handleExternalApiConnectionException(
            ExternalApiConnectionException exception) {
        log.warn("[WARN][{}]", LocalDateTime.now(), exception);
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("외부 서버와의 연결이 불안정합니다.");
        problemDetail.setDetail(exception.getMessage());
        return ResponseEntity.internalServerError().body(problemDetail);
    }

    @ExceptionHandler(RandomNameGenerationException.class)
    public ResponseEntity<ProblemDetail> handleRandomNameGenerationException(RandomNameGenerationException exception) {
        log.warn("[WARN][{}]", LocalDateTime.now(), exception);
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("랜덤 이름 생성에 실패했습니다.");
        problemDetail.setDetail(exception.getMessage());
        return ResponseEntity.internalServerError().body(problemDetail);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ProblemDetail> handleRuntimeException(RuntimeException exception) {
        log.error("[ERROR][{}]", LocalDateTime.now(), exception);
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("예상치 못한 서버 에러 입니다.");
        problemDetail.setDetail(exception.getMessage());
        return ResponseEntity.internalServerError().body(problemDetail);
    }
}
