package finalmission.exception.toilet;

import finalmission.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class ToiletNotFoundException extends ApplicationException {

    private static final String MESSAGE = "화장실이 존재하지 않습니다.";
    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public ToiletNotFoundException() {
        super(STATUS, MESSAGE);
    }
}
