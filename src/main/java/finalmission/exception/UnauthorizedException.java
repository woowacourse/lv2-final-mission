package finalmission.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends CakeDomainException{

    private static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;

    public UnauthorizedException(String message, String errorCode) {
        super(message, HTTP_STATUS, errorCode);
    }

    public static UnauthorizedException jwtTokenInvalid() {
        return new UnauthorizedException("토큰 형식이 잘못되었습니다", "JWT_TOKEN_INVALID");
    }

    public static UnauthorizedException jwtTokenExpired() {
        return new UnauthorizedException("토큰이 만료되었습니다", "JWT_TOKEN_INVALID");
    }

    public static UnauthorizedException jwtTokenEmpty() {
        return new UnauthorizedException("토큰이 비어있습니다", "JWT_TOKEN_EMPTY");
    }

    public static UnauthorizedException passwordMismatch() {
        return new UnauthorizedException("회원을 찾을 수 없습니다.", "PASSWORD_MISMATCH");
    }
}
