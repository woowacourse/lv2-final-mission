package finalmission.auth;

import finalmission.exception.AuthenticationException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

public record AuthToken(
        String value
) {
    private static final String TOKEN_NAME = "auth_token";

    public static AuthToken extract(final HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(TOKEN_NAME)) {
                    return new AuthToken(cookie.getValue());
                }
            }
        }
        throw new AuthenticationException("[ERROR] No auth token found");
    }

    public HttpHeaders toHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        ResponseCookie cookie = ResponseCookie.from(TOKEN_NAME, value)
                .httpOnly(true)
                .sameSite(SameSite.STRICT.name())
                .build();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
        return headers;
    }
}
