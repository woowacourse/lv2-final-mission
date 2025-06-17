package finalmission.global.utils;

import finalmission.global.UnauthorizedException;
import jakarta.servlet.http.Cookie;
import java.util.Arrays;

public final class CookieUtils {

    private CookieUtils() {
    }

    public static Cookie setToken(String token) {
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }

    public static String parseCookie(Cookie[] cookies) {
        if (cookies == null) {
            throw new UnauthorizedException("인증할 수 없는 사용자입니다.");
        }
        return Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("token"))
                .findFirst()
                .orElseThrow(() -> new UnauthorizedException("인증할 수 없는 사용자입니다."))
                .getValue();
    }
}
