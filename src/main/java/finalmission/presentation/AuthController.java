package finalmission.presentation;

import finalmission.application.AuthService;
import finalmission.application.dto.request.LoginRequest;
import finalmission.application.dto.response.LoginResponse;
import jakarta.validation.Valid;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final String TOKEN_COOKIE_KEY = "token";

    private final AuthService authService;
    private final Duration tokenCookieDuration;

    public AuthController(AuthService authService,
                          @Value("${security.jwt.token.expire-duration}") Duration tokenCookieDuration) {
        this.authService = authService;
        this.tokenCookieDuration = tokenCookieDuration;
    }

    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        ResponseCookie jwtCookie = createCookie(TOKEN_COOKIE_KEY, loginResponse.token(), tokenCookieDuration);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .build();
    }

    private ResponseCookie createCookie(String name, String value, Duration maxAge) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(false) //https 적용전 임시
                .path("/")
                .sameSite("Strict")
                .maxAge(maxAge)
                .build();
    }
}
