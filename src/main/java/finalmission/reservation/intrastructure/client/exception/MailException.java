package finalmission.reservation.intrastructure.client.exception;

import finalmission.reservation.intrastructure.client.dto.MailErrorResponse;
import java.util.List;

public class MailException extends RuntimeException {

    public MailException(String state, List<MailErrorResponse> message) {
        super(state + " " + message);
    }

    public MailException(String state, String message) {
        super(state + " " + message);
    }
}
