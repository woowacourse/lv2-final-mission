package finalmission.auth.infrastructure;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

@Component
public class CookieProvider {

    private static final String TOKEN_NAME = "token";

    public Cookie createCookie(final String accessToken) {
        Cookie cookie = new Cookie(TOKEN_NAME,accessToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }
}
