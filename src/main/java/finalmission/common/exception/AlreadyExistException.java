package finalmission.common.exception;

public class AlreadyExistException extends CustomException {

    public AlreadyExistException(String message) {
        super(ErrorCode.CONFLICT, message);
    }
}
