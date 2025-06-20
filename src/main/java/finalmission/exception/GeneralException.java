package finalmission.exception;

import lombok.Getter;

@Getter
public abstract class GeneralException extends RuntimeException {

    private final ErrorCode errorCode;

    protected GeneralException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
