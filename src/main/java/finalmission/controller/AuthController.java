package finalmission.controller;

import finalmission.controller.dto.LoginRequest;
import finalmission.service.AuthService;
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
    public ResponseEntity<Void> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.email(), request.password());
        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .build();
        return ResponseEntity.noContent().header("Set-Cookie", cookie.toString()).build();
    }
}
