package finalmission.global.exception;

public record ErrorResponse(
        String message,
        int errorCode
) {
}
