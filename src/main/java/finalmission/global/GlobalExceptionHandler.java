package finalmission.global;

import finalmission.exception.ErrorCode;
import finalmission.exception.ErrorResponse;
import finalmission.exception.GeneralException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = GeneralException.class)
    public ResponseEntity<ErrorResponse<Void>> handleCustomException(GeneralException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ErrorResponse.error(errorCode.getCode(), errorCode.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse<String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        BindingResult bindingResult = e.getBindingResult();
        for (FieldError er : bindingResult.getFieldErrors()) {
            errors.put(er.getField(), er.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.error(errors, ErrorCode.INVALID_TYPE_VALUE.getCode(),
                        ErrorCode.INVALID_TYPE_VALUE.getMessage()));
    }
}
