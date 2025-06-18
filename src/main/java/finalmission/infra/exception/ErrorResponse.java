package finalmission.infra.exception;


import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public record ErrorResponse(
        LocalDateTime timestamp,

        int status,

        String message
) {

    public static ErrorResponse of(HttpStatus status, String message) {
        LocalDateTime timestamp = LocalDateTime.now();
        return new ErrorResponse(timestamp, status.value(), message);
    }
}
