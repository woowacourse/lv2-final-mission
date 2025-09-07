package lavatoryreservation.auth;

import lavatoryreservation.external.auth.JwtTokenProvider;
import org.springframework.http.ResponseCookie;

public class JwtCookie {

    private static final int ONE_HOUR = 3600;

    private final ResponseCookie responseCookie;

    public JwtCookie(String jwt) {
        responseCookie = ResponseCookie.from(JwtTokenProvider.getCookieKey(), jwt)
                .maxAge(ONE_HOUR)
                .build();
    }

    public String cookie() {
        return responseCookie.toString();
    }
}
