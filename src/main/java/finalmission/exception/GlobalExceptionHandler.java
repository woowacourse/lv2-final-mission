package finalmission.exception;

import finalmission.exception.custom.CannotRemoveException;
import finalmission.exception.custom.DuplicatedValueException;
import finalmission.exception.custom.NotExistedValueException;
import finalmission.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDuplicatedValueException(final DuplicatedValueException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotExistedValueException(final NotExistedValueException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleCannotRemoveException(final CannotRemoveException e) {
        return new ErrorResponse(e.getMessage());
    }
}
