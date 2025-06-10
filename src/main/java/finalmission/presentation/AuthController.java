package finalmission.presentation;

import finalmission.application.LoginService;
import finalmission.application.SignupService;
import finalmission.dto.RequestLogin;
import finalmission.dto.RequestSignup;
import finalmission.infrastructure.security.AccessToken;
import java.net.URI;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private static final String MEMBERS_URI_FORMAT = "/members/%d";

    private final SignupService signupService;
    private final LoginService loginService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody RequestSignup requestSignup) {
        Long id = signupService.signup(requestSignup);
        return ResponseEntity.created(URI.create(MEMBERS_URI_FORMAT.formatted(id))).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AccessToken> login(@RequestBody RequestLogin requestLogin) {
        AccessToken accessToken = loginService.login(requestLogin);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, createCookie(accessToken.value(), Duration.ofDays(1)).toString())
                .build();
    }

    private ResponseCookie createCookie(String value, Duration maxAge) {
        return ResponseCookie.from("token", value)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Strict")
                .maxAge(maxAge)
                .build();
    }
}
