package finalmission.auth.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class JwtCookieResolver {

    private static final String COOKIE_KEY = "token";

    public static String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        return extractTokenFromCookie(cookies);
    }

    private static String extractTokenFromCookie(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(COOKIE_KEY)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
