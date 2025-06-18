package finalmission.auth.infrastructure;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class JwtTokenExtractor {

    private static final String TOKEN_NAME = "token";

    public String extractTokenFromCookie(final Cookie[] cookies) {
         return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(TOKEN_NAME))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 토큰을 찾을 수 없습니다."));
    }
}
