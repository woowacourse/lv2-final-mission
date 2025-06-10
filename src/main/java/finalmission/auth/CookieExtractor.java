package finalmission.auth;

import finalmission.exception.AuthException;
import jakarta.servlet.http.Cookie;
import java.util.Arrays;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieExtractor {

    private static final int LOGIN_COOKIE_MAX_AGE = 3600;
    private static final String COOKIE_NAME = "token";

    public ResponseCookie createCookieByToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new AuthException("토큰이 존재하지 않습니다.");
        }
        return ResponseCookie.from(COOKIE_NAME, token)
                .httpOnly(true)
                .path("/")
                .maxAge(LOGIN_COOKIE_MAX_AGE)
                .build();
    }

    public static String extractToken(Cookie[] cookies) {
        if (cookies == null || cookies.length == 0) {
            throw new AuthException("쿠키가 존재하지 않습니다.");
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(COOKIE_NAME))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthException("쿠키 토큰을 추출할 수 없습니다."));
    }
}
