package finalmission.exception;

import org.springframework.http.HttpStatus;

public class NotFoundDateTimeException extends CustomException {

    public NotFoundDateTimeException() {
        super("예약 시간, 날짜를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }
}
