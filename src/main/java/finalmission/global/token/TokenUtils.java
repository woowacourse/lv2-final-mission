package finalmission.global.token;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class TokenUtils {

    private static final String TOKEN_NAME_FIELD = "token";

    public static String getTokenNameField() {
        return TOKEN_NAME_FIELD;
    }

    public static String extractTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(TOKEN_NAME_FIELD)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
