package woowaTable.user.ui;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import woowaTable.user.application.LoginService;
import woowaTable.user.application.TokenCookieService;
import woowaTable.user.application.dto.LoginCheckRequest;
import woowaTable.user.application.dto.LoginCheckResponse;
import woowaTable.user.application.dto.LoginRequest;
import woowaTable.user.application.dto.SignupRequest;
import woowaTable.user.application.dto.Token;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final TokenCookieService tokenCookieService;

    @Value("${security.jwt.token.access.expire-length}")
    private long expiration;

    @PostMapping("/login/customer")
    public ResponseEntity<Void> loginCustomer(@Valid @RequestBody final LoginRequest request) {
        final Token token = loginService.loginCustomer(request);
        final String cookie = tokenCookieService.createTokenCookie(token.accessToken(), expiration);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie)
                .build();
    }

    @PostMapping("/login/owner")
    public ResponseEntity<Void> loginOwner(@Valid @RequestBody final LoginRequest request) {
        final Token token = loginService.loginOwner(request);
        final String cookie = tokenCookieService.createTokenCookie(token.accessToken(), expiration);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie)
                .build();
    }

    @GetMapping("/login/check")
    public ResponseEntity<LoginCheckResponse> checkLogin(final LoginCheckRequest request) {
        final LoginCheckResponse loginCheckResponse = loginService.checkLogin(request);
        return ResponseEntity.ok(loginCheckResponse);
    }

    @PostMapping("/signup/customer")
    public ResponseEntity<LoginCheckResponse> signupCustomer(@Valid @RequestBody final SignupRequest request) {
        final LoginCheckResponse loginCheckResponse = loginService.signupCustomer(request);
        return ResponseEntity.ok(loginCheckResponse);
    }

    @PostMapping("/signup/owner")
    public ResponseEntity<LoginCheckResponse> signupOwner(@Valid @RequestBody final SignupRequest request) {
        final LoginCheckResponse loginCheckResponse = loginService.signupOwner(request);
        return ResponseEntity.ok(loginCheckResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        final String cookie = tokenCookieService.createTokenCookie("", 0);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie)
                .build();
    }
}
