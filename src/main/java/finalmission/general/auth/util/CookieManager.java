package finalmission.general.auth.util;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieManager {

    public static final long TOKEN_COOKIE_DURATION = 3600L;

    public ResponseCookie generateJwtCookie(String token) {
        return ResponseCookie.from("token", token)
                .httpOnly(true)
                .maxAge(TOKEN_COOKIE_DURATION)
                .build();
    }
}
