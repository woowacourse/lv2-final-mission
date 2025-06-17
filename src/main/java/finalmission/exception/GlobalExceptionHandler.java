package finalmission.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ErrorMessageResponse> handleBadRequestException(Exception e) {
        return ResponseEntity.badRequest()
                .body(new ErrorMessageResponse(HttpStatus.BAD_REQUEST.toString(), e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessageResponse> handleUnhandledException(HttpServletRequest request) {
        LOGGER.info("[]");
        return ResponseEntity.badRequest()
                .body(new ErrorMessageResponse(HttpStatus.BAD_REQUEST.toString(),
                        "예기치 못한 문제가 발생했습니다. 관리자에 문의해 주세요."));
    }
}
