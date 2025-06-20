package finalmission.exception;

public class BadGatewayException extends GeneralException{

    public BadGatewayException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
