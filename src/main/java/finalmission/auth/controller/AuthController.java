package finalmission.auth.controller;

import finalmission.auth.dto.LoginDto;
import finalmission.auth.service.AuthService;
import finalmission.user.domain.dto.UserResponseDto;
import java.time.Duration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        ResponseCookie cookie = ResponseCookie
                .from("token", token)
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(Duration.ofDays(30))
                .sameSite("Lax")
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }

    @GetMapping("/login/check")
    public ResponseEntity<UserResponseDto> checkAuth(@CookieValue(name = "token") String token) {
        UserResponseDto userResponseDto = authService.findMemberByToken(token);
        return ResponseEntity.ok().body(userResponseDto);
    }
}
