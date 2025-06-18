package finalmission.member.controller;

import finalmission.global.auth.util.CookieUtil;
import finalmission.member.dto.request.LoginRequest;
import finalmission.member.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;
    private final CookieUtil cookieUtil;

    public AuthController(AuthService authService, CookieUtil cookieUtil) {
        this.authService = authService;
        this.cookieUtil = cookieUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        String token = authService.login(request);
        Cookie cookie = cookieUtil.createCookie("token", token);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            HttpServletResponse response
    ) {
        Cookie cookie = cookieUtil.expireCookie("token", "");
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }
}
