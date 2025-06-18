package finalmission.common.exception;

public class InvalidInputException extends CustomException {

    public InvalidInputException(String message) {
        super(ErrorCode.INVALID_REQUEST, message);
    }
}
