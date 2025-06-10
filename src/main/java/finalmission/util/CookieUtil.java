package finalmission.util;

import finalmission.exception.custom.UnauthorizedException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {

    private static final int MAX_AGE = 3600;

    private CookieUtil() {
    }

    public static void addCookie(String name, String value, HttpServletResponse response) {
        addCookie(name, value, MAX_AGE, response);
    }

    public static void addCookie(String name, String value, int maxAge, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void expireCookie(String name, HttpServletResponse response) {
        addCookie(name, null, 0, response);
    }

    public static String extractCookie(HttpServletRequest request, String target) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new UnauthorizedException("쿠키가 비어있습니다.");
        }
        return extractTokenFromCookie(cookies, target);
    }

    private static String extractTokenFromCookie(Cookie[] cookies, final String target) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(target)) {
                return cookie.getValue();
            }
        }
        throw new UnauthorizedException("대상 쿠키가 없습니다.");
    }
}
