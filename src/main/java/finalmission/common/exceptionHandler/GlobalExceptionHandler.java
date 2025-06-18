package finalmission.common.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String unauthorizedExceptionHandler(UnauthorizedException e) {
        return e.getMessage();
    }

    @ExceptionHandler(value = HolidayException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String holidayExceptionHandler(HolidayException e) {
        return e.getMessage();
    }
}
