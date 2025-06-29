package finalmission.auth.ui;

import static finalmission.auth.domain.AuthRole.ADMIN;
import static finalmission.auth.domain.AuthRole.MEMBER;

import finalmission.auth.application.AuthService;
import finalmission.auth.domain.RequiresRole;
import finalmission.auth.ui.dto.LoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Void> createAccessToken(
            @RequestBody @Valid final LoginRequest request
    ) {
        final String authToken = authService.createAccessToken(request);

        final ResponseCookie cookie = ResponseCookie.from("token", authToken)
                .path("/")
                .httpOnly(true)
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @PostMapping("/logout")
    @RequiresRole(authRoles = {ADMIN, MEMBER})
    public ResponseEntity<Void> logout() {
        final ResponseCookie cookie = ResponseCookie.from("token", "")
                .path("/")
                .httpOnly(true)
                .maxAge(0)
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
