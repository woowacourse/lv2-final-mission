package finalmission.presentation;

import finalmission.application.support.exception.AuthException;
import finalmission.application.support.exception.BusinessRuleViolationException;
import finalmission.application.support.exception.NotFoundEntityException;
import finalmission.presentation.dto.ApiFailResponse;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiFailResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info("MethodArgumentNotValidException", e);
        String errorMessage = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(new ApiFailResponse(errorMessage));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiFailResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.info("HttpMessageNotReadableException", e);
        return ResponseEntity.badRequest().body(new ApiFailResponse("잘못된 요청입니다."));
    }

    @ExceptionHandler(BusinessRuleViolationException.class)
    public ResponseEntity<ApiFailResponse> handleBusinessRuleViolationException(BusinessRuleViolationException e) {
        log.info("Business rule violation", e);
        return ResponseEntity.badRequest().body(new ApiFailResponse(e.getMessage()));
    }

    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<ApiFailResponse> handleNotFoundEntityException(NotFoundEntityException e) {
        log.info("Not found entity", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiFailResponse(e.getMessage()));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiFailResponse> handleAuthException(AuthException e) {
        log.info("Auth exception", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiFailResponse(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiFailResponse> handleException(Exception e) {
        log.error("예상치 못한 예외 발생: ", e);
        return ResponseEntity.badRequest().body(new ApiFailResponse("예상치 못한 예외가 발생했습니다."));
    }
}
