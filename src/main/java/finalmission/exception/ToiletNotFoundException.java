package finalmission.exception;

import org.springframework.http.HttpStatus;

public class ToiletNotFoundException extends ApplicationException {

    private static final String MESSAGE = "존재하지 않는 화장실입니다.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public ToiletNotFoundException() {
        super(HTTP_STATUS, MESSAGE);
    }
}
