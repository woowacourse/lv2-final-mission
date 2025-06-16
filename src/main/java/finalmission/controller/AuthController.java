package finalmission.controller;

import finalmission.dto.layer.LoginContent;
import finalmission.dto.request.LoginRequest;
import finalmission.servcie.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginContent loginContent = new LoginContent(loginRequest);
        String accessToken = authService.login(loginContent);
        ResponseCookie cookie = ResponseCookie
                .from("access", accessToken)
                .path("/")
                .httpOnly(true)
                .secure(false)
                .build();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie expiredCookie = ResponseCookie
                .from("access", "")
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(0)
                .build();
        return ResponseEntity
                .noContent()
                .header(HttpHeaders.SET_COOKIE, expiredCookie.toString())
                .build();
    }
}
