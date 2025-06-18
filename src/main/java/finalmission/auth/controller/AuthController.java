package finalmission.auth.controller;

import finalmission.auth.controller.dto.request.LoginRequest;
import finalmission.auth.infrastructure.CookieProvider;
import finalmission.auth.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;
    private final CookieProvider cookieProvider;

    public AuthController(final AuthService authService, final CookieProvider cookieProvider) {
        this.authService = authService;
        this.cookieProvider = cookieProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody final LoginRequest request, final HttpServletResponse response) {
        final String accessToken = authService.createToken(request);
        final Cookie cookie = cookieProvider.createCookie(accessToken);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }


}
