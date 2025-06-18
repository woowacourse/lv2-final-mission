package finalmission.exception;

import org.springframework.http.HttpStatus;

public class InvalidGuestSizeException extends CustomException {
    public InvalidGuestSizeException() {
        super("인원 수는 1명 이상, 14명 이하여야 합니다.", HttpStatus.BAD_REQUEST);
    }
}
