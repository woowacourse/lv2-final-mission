package finalmission.common.exception;

import jakarta.servlet.http.HttpServletRequest;

public record ErrorResponse(
        String method,
        String uri,
        int status,
        String message
) {

    public static ErrorResponse of(final HttpServletRequest request, final ErrorCode errorCode) {
        return new ErrorResponse(
                request.getMethod(),
                request.getRequestURI(),
                errorCode.getStatus().value(),
                errorCode.getMessage()
        );
    }
}
