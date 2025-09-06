package finalmission.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CakeDomainException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String errorCode;

    public CakeDomainException(String message, HttpStatus httpStatus, String errorCode) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
}
