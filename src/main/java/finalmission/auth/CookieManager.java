package finalmission.auth;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CookieManager {

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

}
