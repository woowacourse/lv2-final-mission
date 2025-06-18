package finalmission.member.controller;

import finalmission.member.auth.annotation.PermitAll;
import finalmission.member.controller.dto.LoginRequest;
import finalmission.member.controller.dto.SignupRequest;
import finalmission.member.controller.dto.MemberResponse;
import finalmission.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PermitAll
    @PostMapping("/signup")
    public ResponseEntity<MemberResponse> signup(@RequestBody final SignupRequest request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PermitAll
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {
        final String token = authService.login(loginRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", "token=" + token + "; Path=/; HttpOnly");
        headers.add("Keep-Alive", "timeout=60");
        return ResponseEntity.ok().headers(headers).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", "token=; Path=/; HttpOnly; Max-Age=0");
        return ResponseEntity.ok().headers(headers).build();
    }
}
