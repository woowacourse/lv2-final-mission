package finalmission.exception;

public class ForbiddenException extends GeneralException
{
    public ForbiddenException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
