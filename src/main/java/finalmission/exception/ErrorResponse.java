package finalmission.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        String exceptionName,
        String exceptionMessage,
        LocalDateTime timestamp
) {
    public static ErrorResponse from(Exception e) {
        return new ErrorResponse(
                e.getClass().getSimpleName(),
                e.getMessage(),
                LocalDateTime.now()
        );
    }
}
