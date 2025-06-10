package finalmission.presentation;

import finalmission.dto.request.LoginRequest;
import finalmission.service.AuthService;
import finalmission.service.PasswordResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final Long cookieMaxAge;

    public AuthController(
            AuthService authService,
            @Value("${auth.cookie.max-age}") Long cookieMaxAge
    ) {
        this.authService = authService;
        this.cookieMaxAge = cookieMaxAge;
    }

    @PostMapping
    public ResponseEntity<Void> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        ResponseCookie cookie = ResponseCookie.from("token")
                .value(token)
                .httpOnly(true)
                .maxAge(cookieMaxAge)
                .path("/")
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }

    @GetMapping("/password-recommend")
    public ResponseEntity<PasswordResponse> recommendPassword() {
        PasswordResponse response = authService.recommendPassword();
        return ResponseEntity.ok(response);
    }
}
