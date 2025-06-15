package finalmission.auth.presentation;

import finalmission.auth.dto.request.LoginRequest;
import finalmission.auth.service.AuthService;
import finalmission.member.infrastructure.MemberRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final Long cookieMaxAge;
    private final AuthService authService;

    public AuthController(
            @Value("${auth.cookie.max-age}") Long cookieMaxAge,
            AuthService authService, MemberRepository memberRepository
    ) {
        this.cookieMaxAge = cookieMaxAge;
        this.authService = authService;
    }

    @PostMapping("/auth")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginRequest request) {
        String token = authService.login(request);
        ResponseCookie cookie = ResponseCookie.from("token")
                .value(token)
                .httpOnly(true)
                .maxAge(cookieMaxAge)
                .path("/")
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }
}
