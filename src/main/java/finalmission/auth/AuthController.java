package finalmission.auth;

import finalmission.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    public static final String TOKEN_NAME = "TOKEN";

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody String phoneNumber) {
        final String jwt = authService.generateToken(phoneNumber);
        final ResponseCookie responseCookie = ResponseCookie
                .from(TOKEN_NAME, jwt)
                .secure(true)
                .httpOnly(true)
                .sameSite(SameSite.LAX.attributeValue())
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .build();
    }

    @GetMapping("/check")
    public ResponseEntity<Member> check(@Auth final Member member) {
        return ResponseEntity.ok(member);
    }
}
