package finalmission.mungPlan.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ProblemDetail handlerCustomException(CustomException e) {
        log.warn("{} 발생 - {}", e.getHttpStatus(), e.getMessage());
        return ProblemDetail.forStatusAndDetail(e.getHttpStatus(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpectedException(Exception e) {
        log.error("예상치 못한 예외 발생: ", e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류 발생");
    }
}
