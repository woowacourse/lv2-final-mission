package finalmission.auth;

import jakarta.servlet.http.Cookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CookieManager {

    public ResponseCookie create(String token) {
        return ResponseCookie.from("token", token)
                .secure(true)
                .httpOnly(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(24 * 60 * 60)
                .build();
    }

    public ResponseCookie invalidate(Cookie cookie) {
        return ResponseCookie.from("token", cookie.getValue())
                .maxAge(0)
                .path("/")
                .httpOnly(true)
                .sameSite("Strict")
                .build();
    }

    public String extractTokenFromCookies(final Cookie[] cookies) {
        if (cookies == null) {
            throw new IllegalArgumentException("쿠키의 토큰 정보가 유효하지 않습니다.");
        }

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("token"))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String extractTokenFromCookie(final Cookie cookie) {
        if (cookie == null) {
            throw new IllegalArgumentException();
        }

        return cookie.getValue();
    }
}
