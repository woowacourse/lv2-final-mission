package finalmission.common;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class TokenCookieManager {

    private static final String TOKEN_FILED = "token";
    private static final String TOKEN_COOKIE_DEFAULT_PATH = "/";

    public void addTokenCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(TOKEN_FILED, token);
        cookie.setHttpOnly(true);
        cookie.setPath(TOKEN_COOKIE_DEFAULT_PATH);
        response.addCookie(cookie);
    }

    public String extractTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = getCookies(request);
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(TOKEN_FILED)) {
                return cookie.getValue();
            }
        }
        return "";
    }

    private Cookie[] getCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            // TODO: 권한 에러 커스텀
            throw new IllegalArgumentException("로그인 되어있지 않습니다.");
        }
        return cookies;
    }
}
