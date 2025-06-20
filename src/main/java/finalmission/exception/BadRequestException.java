package finalmission.exception;

public class BadRequestException extends GeneralException{
    public BadRequestException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
