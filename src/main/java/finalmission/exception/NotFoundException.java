package finalmission.exception;

public class NotFoundException extends GeneralException{
    public NotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
