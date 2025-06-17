package finalmission.auth.controller;

import finalmission.auth.dto.LoginRequest;
import finalmission.auth.service.AuthService;
import finalmission.global.utils.CookieUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<Void> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        String token = authService.login(request);
        Cookie cookie = CookieUtils.setToken(token);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }
}
